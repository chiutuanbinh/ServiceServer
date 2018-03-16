/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
/**
 *
 * @author root
 */
public final class Cache {
    
    private HashMap<String,Node> infoTable ;
    private CustomLinkedList<ProfileInfo> LRUQueue;
    private static final int MAX_CACHE_SIZE = ServerSetting.getMaxLRUSize();
    private int cacheSize = 0;
    private final String DBSetting = ServerSetting.getDBType();
    private static final Cache INSTANCE = new Cache();
    private Cache(){
        infoTable = new HashMap<>(MAX_CACHE_SIZE);
	LRUQueue = new CustomLinkedList<>();
    }
    
    private ExecutorService executor = Executors.newFixedThreadPool(100);
    
    public static Cache getInstance(){
        return INSTANCE;
    }
    
    public boolean setVal(String key, ProfileInfo element) {
	//Adjust the cache
	boolean result = true;
	if (this.cacheSize == MAX_CACHE_SIZE){
	    ProfileInfo lastNodeItem = this.LRUQueue.removeLast();
	    this.infoTable.remove(lastNodeItem.id);
	    this.LRUQueue.addFirst(element);
	    this.infoTable.put(key, this.LRUQueue.getFirstNode());
	}
	else{
	    this.LRUQueue.addFirst(element);
	    this.infoTable.put(key, this.LRUQueue.getFirstNode());
	    this.cacheSize ++;
	}
	SyncToDB saveSync = new SyncToDB(0, element, key);
	executor.submit(saveSync);
	//System.out.println("the after instruction");
	return result;
    }
    
    
    public ProfileInfo getVal(String key){
	ProfileInfo result = null;
	//The cache have the data
	if (this.infoTable.containsKey(key)){
	    Node<ProfileInfo> eleNode = this.infoTable.get(key);
	    this.LRUQueue.moveToFirst(eleNode);
	    result = this.LRUQueue.getElement(eleNode);
	}
	else {
	    //Cannot find the data, read the properties file to know where to look for the data
	    if (DBSetting.equals("NoSQL")){
		result = NoSQLConnection.getInstance().getFromBD(key);
		if (result == null){
		    return result;
		}
		
	    }
	    else if (DBSetting.equals("MySQL")){
		result = SqlConnection.getInstance().getFromDB(key);
		if (result == null){
		    return result;
		}
	    }
	    //Adjust the cache table
//	    this.syncCache(key, result, 2); already done in database connection layer
	}
	return result;
    }
    
    
    public boolean updateVal(ProfileInfo element){
	String eleKey = element.id;
	boolean result = false;
	if (this.infoTable.containsKey(eleKey)){
	    Node<ProfileInfo> eleNode = this.infoTable.get(eleKey);
	    this.LRUQueue.setElement(eleNode, element);
	    this.LRUQueue.moveToFirst(eleNode);
	    
	    SyncToDB updateSync = new SyncToDB(1, element, eleKey);
	    executor.submit(updateSync);
	    return true;
	}
	else{
	    if (DBSetting.equals("NoSQL")){
		result = NoSQLConnection.getInstance().updateToDB(element);
	    }
	    else if (DBSetting.equals("MySQL")){
		result = SqlConnection.getInstance().updateToDB(element);
	    }
	    return result;
	}
    }
    
    public boolean removeVal(String key){
	boolean result = false;
	if (this.infoTable.containsKey(key)){

	    Node<ProfileInfo> eleNode = this.infoTable.get(key);
	    this.infoTable.remove(key);
	    this.LRUQueue.remove(eleNode);
	    
	    this.cacheSize -= 1;
	    SyncToDB removeSync = new SyncToDB(2, null, key);
	    executor.submit(removeSync);
	}
	else{
	    if (DBSetting.equals("NoSQL")){
		result = NoSQLConnection.getInstance().removeFromDB(key);
	    }
	    else if (DBSetting.equals("MySQL")){
		result = SqlConnection.getInstance().removeFromDB(key);
	    }     
	}
	return result;
    }
    
    
    //Update the cache when a value is delete from the database or change, change it when db is set or look up
    //@opType is 0 for removeOp, is 1 for updateOp, other if save or get
    public synchronized boolean syncCache(String key, ProfileInfo item, int opType){
	if (item == null)
	    return false;
	switch (opType){
	    case 0:
		//in case some client fetch the old data to cache before it could be 
		//deleted from the DB.
		//remove it again 
		if (this.infoTable.containsKey(key)){ 
		    Node<ProfileInfo> rmNode = this.infoTable.remove(key);
		    this.LRUQueue.remove(rmNode);
		    this.cacheSize--;
		}
		break;
		
	    case 1:
		//in case some client fetch the old data to cache before it could be 
		//updated in the DB
		//refetch it
		//if the cache doesn't have the data, fetch it
		if (this.infoTable.containsKey(key)){
		    Node<ProfileInfo> oldNode = this.infoTable.get(key);
		    this.LRUQueue.setElement(oldNode,item);
		}
		else{
		    if (this.cacheSize == MAX_CACHE_SIZE){
			String rmNodeKey = this.LRUQueue.removeLast().id;
			this.infoTable.remove(rmNodeKey);
		    }
		    else{
			this.cacheSize ++;
		    }
		    this.LRUQueue.addFirst(item);
		    this.infoTable.put(key, this.LRUQueue.getFirstNode());
		}
		break;
		
	    case 2:
		/*If the data is fetch from the database since client cant get it
		from cache
		*/
		if (this.infoTable.containsKey(key))
		    break;
		else{
		    if (this.cacheSize == MAX_CACHE_SIZE){
			String rmNodeKey = this.LRUQueue.removeLast().id;
			this.infoTable.remove(rmNodeKey);
		    }
		    else{
			this.cacheSize ++;
		    }
		    this.LRUQueue.addFirst(item);
		    this.infoTable.put(key, this.LRUQueue.getFirstNode());
		}
		break;
	}
	return true;
    }
    
    private class SyncToDB implements Runnable{
	int opType;
	ProfileInfo data;
	String dataKey;
	
	public SyncToDB(int caller, ProfileInfo inputData, String inputKey){
	    this.opType = caller;
	    this.data = inputData;
	    this.dataKey = inputKey;
	}
	
	@Override
	public void run(){
	    //System.out.println("DataBase Sync Thread Start");
	    switch (this.opType){
	    case 0:// save operation
		if (DBSetting.equals("NoSQL")){
		    NoSQLConnection.getInstance().saveToDB(data);
		}
		else if (DBSetting.equals("MySQL")){
		    SqlConnection.getInstance().saveToDB(data);
		}
		break;
	    case 1: // update operation
		if (DBSetting.equals("NoSQL")){
		    NoSQLConnection.getInstance().updateToDB(data);
		}
		else if (DBSetting.equals("MySQL")){
		    SqlConnection.getInstance().updateToDB(data);
		}
		break;
	    case 2: // remove operation
		if (DBSetting.equals("NoSQL")){
		    NoSQLConnection.getInstance().removeFromDB(dataKey);
		}
		else if (DBSetting.equals("MySQL")){
		    SqlConnection.getInstance().removeFromDB(dataKey);
		}
		
	    }
	    
	}
    }
    
    
    //for debug purpose
    public HashMap getHashMap(){
	return this.infoTable;
    }
}