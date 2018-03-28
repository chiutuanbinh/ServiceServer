/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server.cache;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import com.vng.zing.zalotraing.server.database.NoSQLConnection;
import com.vng.zing.zalotraing.server.ProfileInfo;
import com.vng.zing.zalotraing.server.ServerSetting;
import com.vng.zing.zalotraing.server.database.SqlConnection;
/**
 *
 * @author root
 */
public final class Cache implements CacheIface{
    
    private HashMap<String,Node> infoTable ;
    private CustomLinkedList<ProfileInfo> LRUQueue;
    private static final int MAX_CACHE_SIZE = ServerSetting.getMaxLRUSize();
    private int cacheSize = 0;
    private final String DBSetting = ServerSetting.getDBType();
    private ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final Cache INSTANCE = new Cache();
    private final NoSQLConnection noSQLConnection = NoSQLConnection.getInstance();
    private final SqlConnection sqlConnection = SqlConnection.getInstance();
    
    private Cache(){
        infoTable = new HashMap<>(MAX_CACHE_SIZE);
	LRUQueue = new CustomLinkedList();
    }
    
    
    
    public static synchronized Cache getInstance(){
        return INSTANCE;
    }
    
    public boolean setVal(String key, ProfileInfo element) {
	boolean result = true;
	if (this.infoTable.containsKey(key))
		return false;
	else if (this.cacheSize == MAX_CACHE_SIZE){
	    ProfileInfo lastNodeItem = this.LRUQueue.removeLast();
	    this.infoTable.remove(lastNodeItem.id);
	    this.LRUQueue.addFirst(element);
	    Node<ProfileInfo> firstNode = this.LRUQueue.getFirstNode();
	    this.infoTable.put(key, firstNode);
	}
	else{
		this.LRUQueue.addFirst(element);
		Node<ProfileInfo> firstNode = this.LRUQueue.getFirstNode();
		this.infoTable.put(key, firstNode);
		this.cacheSize ++;
	}
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
	return result;
    }
    
    
    public boolean updateVal(ProfileInfo element){
	String eleKey = element.id;
	boolean result = false;
	if (this.infoTable.containsKey(eleKey)){
	    Node<ProfileInfo> eleNode = this.infoTable.get(eleKey);
	    this.LRUQueue.setElement(eleNode, element);
	    this.LRUQueue.moveToFirst(eleNode);
	    
	    result = true;
	}
	return result;
    }
    
    public boolean removeVal(String key){
	boolean result = false;
	if (this.infoTable.containsKey(key)){
	    
	    Node<ProfileInfo> eleNode = this.infoTable.get(key);
	    //System.out.println(eleNode);
	    this.LRUQueue.remove(eleNode);
	    this.infoTable.remove(key);
	    
	    
	    this.cacheSize -= 1;
	    result = true;
	}
	return result;
    }
    
    
    //Update the cache when a value is delete from the database or change, change it when db is set or look up
    //@opType is 0 for removeOp, is 1 for updateOp, other if save or get
    public  boolean syncCache(String key, ProfileInfo item, int opType){
	if (item == null)
	    return false;
	switch (opType){
	    case 0:
		//in case some client fetch the old data to cache before it could be 
		//deleted from the DB.
		//remove it again 
		if (this.infoTable.containsKey(key)){ 
		    Node<ProfileInfo> rmNode = this.infoTable.get(key);
		    this.LRUQueue.remove(rmNode);
		    this.infoTable.remove(key);
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
    
    
    //for debug purpose
    public HashMap getHashMap(){
	return this.infoTable;
    }
}
