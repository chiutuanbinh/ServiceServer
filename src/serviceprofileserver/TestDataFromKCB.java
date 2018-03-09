/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;
import java.util.Iterator;
import kyotocabinet.*;
import org.apache.commons.lang.SerializationUtils;
/**
 *
 * @author root
 */
public class TestDataFromKCB {
    private static String dbPath = "userProfile.kch";
    public static void printall(){
	DB db = new DB();
	db.open(dbPath, DB.OREADER);
	
	String key = "3";
	Cursor csCursor = db.cursor();
	csCursor.jump();
	byte[] rec1;
	System.out.println("alert");
	while ((rec1 = csCursor.get_key(true)) != null) {
	    System.out.println(rec1);
	    System.out.println(key.getBytes());
	}
	csCursor.disable();
    }
}
