/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import kyotocabinet.Cursor;
import kyotocabinet.DB;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class ShowAllDB {
    
    public ShowAllDB() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
        @Test
    public void showDBFile(){
	DB db = new DB();
	
	db.open("userProfile.kch", DB.OWRITER);
	
	Cursor cs = new Cursor(db);
	
	cs.jump();
	String[] rec = null;
	long count = 0;
	while((rec = cs.get_str(true)) != null){
	    //System.out.println(rec[0]);
	    count++;
	}
	System.out.print("#####" + count);
	cs.disable();
//	db.clear();
	db.close();
    }
}
