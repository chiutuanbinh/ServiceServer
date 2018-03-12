/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

/**
 *
 * @author root
 */
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import kyotocabinet.*;
import org.apache.commons.lang.SerializationUtils;
public final class NoSQLConnection {
    
    private static final int POOL_SIZE = ServerSetting.getConnectionPoolSize();
    private static BlockingQueue<DB> dbPool = new LinkedBlockingQueue<>(POOL_SIZE);
    
    private static String dbPath = "userProfile.kch";
    
    
    private static final NoSQLConnection INSTANCE = new NoSQLConnection();
    
    private NoSQLConnection(){
	
	//Init the Connection Pool 
	for (int i = 0; i < POOL_SIZE ; i ++) {
	    DB dbi = new DB();
	    dbi.open(dbPath, DB.OWRITER | DB.OCREATE | DB.OREADER );
	    try {
		dbPool.put(dbi);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    
	}
	System.out.println("NoSQL connections established");
    }
    
    
    //save to Db, transform the profileInfo Obj into byte stream data
    public boolean saveToDB(ProfileInfo saveItem){
	
	boolean succeed = false;
        try {
	    DB conn = dbPool.take();
	    //System.out.println("get a connection W");
	    if (conn.add(saveItem.id, Util.ProfileInfoToString(saveItem))){
//		System.out.println("Trying to return the connection W");
		dbPool.offer(conn);
//		System.out.println("return the connection W" + Util.ObjToString(saveItem));
		succeed = true;
	    }
	    else{
		dbPool.offer(conn);
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
	    System.out.println("get a connection");
	    String Item = conn.get(key);
	    if (Item == null){
		dbPool.offer(conn);
		System.out.println("return the connection, not found");
	    }
	    else{
		result = Util.StringToProfileInfo(Item);
		dbPool.offer(conn);
		System.out.println("return the connection");
	    }
	} catch (Exception e) {
	}
        return result;
    }
    

    //update the item in the DB to a new value
    public boolean updateToDB(ProfileInfo updateItem){
	boolean result = false;
	try {
	    DB conn = dbPool.take();
	    if (conn.set(updateItem.id, Util.ProfileInfoToString(updateItem))){
		HashTable.getInstance().syncCache(updateItem.id, updateItem, 1);
		result = true;
	    }
	    dbPool.offer(conn);
	} catch (Exception e) {
	}
	
	return result;
	
    }
    

    //remove the item from the DB
    public boolean removeFromDB(String key){
	boolean result = false;
	try {
	    DB conn = dbPool.take();
	
	    if (conn.remove(key)){
		ProfileInfo dummyItem = new ProfileInfo();
		HashTable.getInstance().syncCache(key,dummyItem, 0);
		result = true;
	    }
	    dbPool.offer(conn);
	} catch (Exception e) {
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
