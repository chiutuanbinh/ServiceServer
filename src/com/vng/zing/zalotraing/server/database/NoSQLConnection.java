/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server.database;

/**
 *
 * @author root
 */
import com.vng.zing.zalotraing.server.ProfileInfo;
import com.vng.zing.zalotraing.server.ServerSetting;
import com.vng.zing.zalotraing.server.Util;
import com.vng.zing.zalotraing.server.cache.Cache;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import kyotocabinet.*;
public final class NoSQLConnection {
    
    private static final int POOL_SIZE = ServerSetting.getConnectionPoolSize();
    private static BlockingQueue<DB> dbPool = new LinkedBlockingQueue<>(POOL_SIZE);
    private static String dbPath = "userProfile.kch";
    private int activeConnection = 0;
    
    private static final NoSQLConnection INSTANCE = new NoSQLConnection();
    
    private NoSQLConnection(){
	
	//Init the Connection Pool with one connection
	DB dbi = new DB();
	dbi.open(dbPath, DB.OWRITER | DB.OCREATE);
	this.activeConnection ++;
	try {
	    dbPool.offer(dbi);
	    for (int i = 1; i < POOL_SIZE; i++) {
		dbPool.offer(createConnection());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println("NoSQL connections established");
    }
    
    private synchronized DB createConnection() throws InterruptedException{
	if (activeConnection < POOL_SIZE){
	    DB db = new DB();
	    db.open(dbPath, DB.OWRITER);
	    this.activeConnection ++;
	    return db;
	}
	else {
	    return dbPool.take();
	}
    
    }
    
    //save to Db, transform the profileInfo Obj into byte stream data
    public boolean saveToDB(ProfileInfo saveItem){
	
	boolean succeed = false;
        try {
	    DB conn = dbPool.take();
	    if (conn == null)
		conn = createConnection();
	    //System.out.println("get a connection W");
	    if (conn.set(saveItem.id, Util.ProfileInfoToString(saveItem))){
//		System.out.println("Trying to return the connection W");
		boolean added = dbPool.offer(conn);
		if (!added){
		    conn.close();
		    this.activeConnection --;
		}
//		System.out.println("return the connection W" + Util.ObjToString(saveItem));
		succeed = true;
	    }
	    else{
		boolean added = dbPool.offer(conn);
		if (!added){
		    conn.close();
		    this.activeConnection --;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
        return succeed;
    }
    
    
    //get from Db, turn the stream data back to obj
    public ProfileInfo getFromBD(String key){
	ProfileInfo result = null;
	try {
	    DB conn = dbPool.take();
	    if (conn == null)
		conn = createConnection();
//	    System.out.println("get a connection");
	    String Item = conn.get(key);
	    if (Item == null){
		
		boolean added = dbPool.offer(conn);
		if (!added){
		    conn.close();
		    this.activeConnection --;
		}
//		System.out.println("return the connection, not found");
	    }
	    else{
		result = Util.StringToProfileInfo(Item);
		boolean added = dbPool.offer(conn);
		if (!added){
		    conn.close();
		    this.activeConnection --;
		}
//		System.out.println("return the connection");
	    }
	} catch (Exception e) {
	}
	Cache.getInstance().syncCache(key, result, 2);
        return result;
    }
    

    //update the item in the DB to a new value
    public boolean updateToDB(ProfileInfo updateItem){
	boolean result = false;
	try {
	    DB conn = dbPool.take();
	    if (conn == null){
		conn = createConnection();
	    }
	    if (conn.set(updateItem.id, Util.ProfileInfoToString(updateItem))){
		Cache.getInstance().syncCache(updateItem.id, updateItem, 1);
		result = true;
	    }
	    boolean added = dbPool.offer(conn);
	    if (!added){
		conn.close();
		this.activeConnection --;
	    }
	} catch (Exception e){
	    e.printStackTrace();
	}
	
	return result;
	
    }
    

    //remove the item from the DB
    public boolean removeFromDB(String key){
	boolean result = false;
	try {
	    DB conn = dbPool.take();
	    if (conn == null){
		conn = createConnection();
	    }
	
	    if (conn.remove(key)){
		ProfileInfo dummyItem = new ProfileInfo();
		Cache.getInstance().syncCache(key,dummyItem, 0);
		result = true;
	    }
	    boolean added = dbPool.offer(conn);
	    if (!added){
		conn.close();
		this.activeConnection --;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return result;
    }
    
    public static NoSQLConnection getInstance(){
        return INSTANCE;
    }
    
    @Override
    protected void finalize() throws Throwable{
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
		dbPool.take().close();
	    }
	    dbPool.clear();
        } 
        finally {
            super.finalize();
        }
        
    }
}
