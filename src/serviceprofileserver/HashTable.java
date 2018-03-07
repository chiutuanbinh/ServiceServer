/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.util.HashMap;
import java.util.LinkedList;
/**
 *
 * @author root
 */
public final class HashTable {
    private static final HashTable INSTANCE = new HashTable(20);
    private HashMap<String,ProfileInfo> infoTable ;
    private LinkedList<String> LRUQueue;
    private static final int MAX_CACHE_SIZE = 20;
    private int cacheSize = 0;
    private final String DBSetting = ServerSetting.getInstance().getDBType();
    private HashTable(int i){
        infoTable = new HashMap<>(i);
	LRUQueue = new LinkedList<>();
    }
    
    public static HashTable getInstance(){
        return INSTANCE;
    }
    
    public boolean setVal(String key, ProfileInfo element) {
	//Adjust the cache
	boolean result = false;
	this.syncCache(key, element,2);
	
	if (DBSetting.equals("NoSQL")){
	    result = NoSQLConnection.getInstance().saveToDB(element);
	}
	else if (DBSetting.equals("MySQL")){
	    result = SqlConnection.getInstance().saveToDB(element);
	}
	return result;
    }
    
    
    public ProfileInfo getVal(String key){
	ProfileInfo result = null;
	//The cache have the data
	if (this.infoTable.containsKey(key)){
	    this.LRUQueue.remove(key);
	    this.LRUQueue.addFirst(key);
	    return this.infoTable.get(key);
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
	    this.syncCache(key, result, 2);
	}
	return result;
    }
    
    
    public boolean updateVal(ProfileInfo element){
	String eleKey = element.id;
	boolean result = false;
	if (this.infoTable.containsKey(eleKey)){
	    this.LRUQueue.remove(eleKey);
	    this.LRUQueue.addFirst(eleKey);
	    this.infoTable.remove(eleKey);
	    this.infoTable.putIfAbsent(eleKey, element);
	}
	if (DBSetting.equals("NoSQL")){
	    result = NoSQLConnection.getInstance().updateToDB(element);
	}
	else if (DBSetting.equals("MySQL")){
	    result = SqlConnection.getInstance().updateToDB(element);
	}
	return result;
    }
    
    public boolean removeVal(String key){
	boolean result = false;
	if (this.infoTable.containsKey(key)){
	    this.LRUQueue.remove(key);
	    this.infoTable.remove(key);
	    this.cacheSize -= 1;
	}
	if (DBSetting.equals("NoSQL")){
	    result = NoSQLConnection.getInstance().removeFromDB(key);
	}
	else if (DBSetting.equals("MySQL")){
	    result = SqlConnection.getInstance().removeFromDB(key);
	}  
	return result;
    }
    
    
    //Update the cache when a value is delete from the database or change, change it when db is set or look up
    //@opType is 0 for removeOp, is 1 for updateOp, other if save or get
    public synchronized boolean  syncCache(String key, ProfileInfo item, int opType){
	switch (opType){
	    case 0:
		if (this.infoTable.containsKey(key)){
		    this.infoTable.remove(key);
		    this.LRUQueue.remove(key);
		    this.cacheSize -= 1;
		}
		break;
	    case 1:
		if (this.infoTable.containsKey(key)){
		    this.infoTable.remove(key);
		    this.infoTable.put(key, item);
		    this.LRUQueue.remove(key);
		    this.LRUQueue.addFirst(key);
		}
		else {
		    if (this.cacheSize == MAX_CACHE_SIZE){
			String removedKeyString = this.LRUQueue.removeLast();
			this.LRUQueue.addFirst(key);
			this.infoTable.remove(removedKeyString);
			this.infoTable.put(key, item);
		    }
			else {
			this.LRUQueue.addFirst(key);
			this.infoTable.put(key, item);
			this.cacheSize += 1;
		    }
		}
		break;
	    default:
		//fix the case where duplicate data is put into hashtable
		if (this.infoTable.containsKey(key)){
		    this.LRUQueue.remove(key);
		    this.LRUQueue.addFirst(key);
		}
		else{
		    if (this.cacheSize == MAX_CACHE_SIZE){
			String removedKeyString = this.LRUQueue.removeLast();
			this.LRUQueue.addFirst(key);
			this.infoTable.remove(removedKeyString);
			this.infoTable.put(key, item);
		    }
		    else {
			this.LRUQueue.addFirst(key);
			this.infoTable.put(key, item);
			this.cacheSize += 1;
		    }
		}
	}
	return true;
    }
}
