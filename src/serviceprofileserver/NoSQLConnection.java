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
public final class NoSQLConnection {
    private static final NoSQLConnection INSTANCE = new NoSQLConnection();
    private static DB db;
    private NoSQLConnection(){
        db = new DB();
        
        //open the database
        if (!db.open("userProfile.kch", DB.OWRITER | DB.OCREATE | DB.OREADER)){
            System.err.println("open error: " + db.error());
        }
//        db.set("1".getBytes(), SerializationUtils.serialize(new profileInfo("A", "A@abc", "922323", new day(12, 12, 1983), "1")));
//        db.set("2".getBytes(), SerializationUtils.serialize(new profileInfo("B", "B@abc", "345678", new day(1, 1, 1888), "2")));
    }
    //save to Db, transform the profileInfo Obj into byte stream data
    public boolean saveToDB(ProfileInfo saveItem){
        byte[] bKey = SerializationUtils.serialize(saveItem.id);
        byte[] bSaveItem = SerializationUtils.serialize(saveItem);
        if (db.add(bKey, bSaveItem))
            return true;
        return false;
    }
    //get from Db, turn the stream data back to obj
    public ProfileInfo getFromBD(String key){
        byte[] bItem = db.get(key.getBytes());
	if (bItem == null){
	    return null;
	}
	else{
	    //Object o = SerializationUtils.deserialize(bItem);
	    ProfileInfo item = (ProfileInfo) SerializationUtils.deserialize(bItem);
	    return item;
	}
    }
    //update the item in the DB to a new value
    public boolean updateToDB(ProfileInfo updateItem){
	if (db.set(updateItem.id.getBytes(), SerializationUtils.serialize(updateItem))){
	    HashTable.getInstance().syncCache(updateItem.id, updateItem, 1);
	    return true;
	}
	else
	    return false;
    }
    //remove the item from the DB
    public boolean removeFromDB(String key){
	if (db.remove(key.getBytes())){
	    ProfileInfo dummyItem = new ProfileInfo();
	    HashTable.getInstance().syncCache(key,dummyItem, 0);
	    return true;
	}
	else{
	    return false;
	}
	
    }
    
    public static NoSQLConnection getInstance(){
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
