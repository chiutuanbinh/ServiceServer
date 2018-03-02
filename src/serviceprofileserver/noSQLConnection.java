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
import kyotocabinet.*;
import org.apache.commons.lang.SerializationUtils;
public final class noSQLConnection {
    private static final noSQLConnection INSTANCE = new noSQLConnection();
    public static DB db;
    private noSQLConnection(){
        db = new DB();
        
        //open the database
        if (!db.open("userProfile.kch", DB.OWRITER | DB.OCREATE)){
            System.err.println("open error: " + db.error());
        }
//        db.set("1".getBytes(), SerializationUtils.serialize(new profileInfo("A", "A@abc", "922323", new day(12, 12, 1983), "1")));
//        db.set("2".getBytes(), SerializationUtils.serialize(new profileInfo("B", "B@abc", "345678", new day(1, 1, 1888), "2")));
    }
    //save to Db, transform the profileInfo Obj into byte stream data
    public boolean saveToDB(profileInfo saveItem){
        byte[] bKey = saveItem.id.getBytes();
        byte[] bSaveItem = SerializationUtils.serialize(saveItem);
        if (!db.set(bKey, bSaveItem))
            return true;
        return false;
    }
    //get from Db, turn the stream data back to obj
    public profileInfo getFromBD(String key){
        byte[] bItem = db.get(key.getBytes());
        profileInfo item = (profileInfo)SerializationUtils.deserialize(bItem);
        return item;
    }
    public static noSQLConnection getInstance(){
        return INSTANCE;
    }
    
    @Override
    protected void finalize() throws Throwable{
        try {
            db.close();
        } 
        finally {
            super.finalize();
        }
        
    }
}
