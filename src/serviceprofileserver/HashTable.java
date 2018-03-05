/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author root
 */
public final class HashTable {
    private static final HashTable INSTANCE = new HashTable(20);
    private HashMap<String,profileInfo> infoTable ;
    private LinkedList<String> LRUQueue;
    private static final int MAX_CACHE_SIZE = 20;
    private int cacheSize = 0;
    
    private HashTable(int i){
        infoTable = new HashMap<>(i);
	LRUQueue = new LinkedList<>();
    }
    
    public static HashTable getInstance(){
        return INSTANCE;
    }
    //TODO: implement
    public boolean setVal(String key, profileInfo element){
	
	return true;
    }
    //TODO: implement
    public profileInfo getVal(String key) throws Exception{
	profileInfo result = null;
	//The cache have the data
	if (this.infoTable.containsKey(key)){
	    this.LRUQueue.remove(key);
	    this.LRUQueue.add(key);
	    return this.infoTable.get(key);
	}
	else {
	    //Cannot find the data, read the properties file to know where to look for the data
	    String DBSetting = ServerSetting.getDBType();
	    if (DBSetting.equals("NoSQL")){
		result = noSQLConnection.getInstance().getFromBD(key);
		if (result == null){
		    return result;
		}
		
	    }
	    else if (DBSetting.equals("MySQL")){
		result = sqlConnection.getInstance().getFromDB(key);
		if (result == null){
		    return result;
		}
	    }
	    //Adjust the cache table
	    if (this.cacheSize > MAX_CACHE_SIZE){
		throw new Exception("Error cache size, bigger than limit");
	    }
	    else if (this.cacheSize == MAX_CACHE_SIZE){
		this.LRUQueue.removeLast();
		this.LRUQueue.addFirst(key);
		this.infoTable.put(key, result);
	    }
	    else { 
		this.LRUQueue.addFirst(key);
		this.infoTable.put(key, result);
		this.cacheSize += 1;
	    }
	}
	return null;
    }
}
