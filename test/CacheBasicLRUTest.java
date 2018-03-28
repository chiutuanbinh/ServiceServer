/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.vng.zing.zalotraing.server.Day;
import com.vng.zing.zalotraing.server.cache.Cache;
import com.vng.zing.zalotraing.server.database.NoSQLConnection;
import com.vng.zing.zalotraing.server.ProfileInfo;
import com.vng.zing.zalotraing.server.handler.ProfileServiceHandlerWithTimeMeasurer;
import com.vng.zing.zalotraing.server.ServerSetting;
import com.vng.zing.zalotraing.server.database.SqlConnection;

/**
 *
 * @author root
 */
public class CacheBasicLRUTest {
    
    ProfileInfo data0 = new ProfileInfo("Donald Trump", "Trump@gmail", "19006067", new Day(6, 9, 1968), "9999999");
    ProfileInfo data1 = new ProfileInfo("John Rambo", "Bo@gmail", "19961234", new Day(6, 9, 1969), "9999998");
    ProfileInfo data2 = new ProfileInfo("David Beckham", "Beck@gmail", "19006767", new Day(6, 9, 1968), "9999997");
    ProfileInfo data3 = new ProfileInfo("Nguyen Van A", "A@gmail", "18006083", new Day(6, 9, 1968), "9999996");
    Cache cache = Cache.getInstance();
    ProfileServiceHandlerWithTimeMeasurer PSH = new ProfileServiceHandlerWithTimeMeasurer();
    
    public CacheBasicLRUTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	SqlConnection.getInstance();
	NoSQLConnection.getInstance();
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
    public void loadPropertiesShouldBeCorrect(){
	System.out.println(ServerSetting.getDBType());
	System.out.println(ServerSetting.getConnectionPoolSize());
    }
    
    
    @Test
    public void addDataShouldNotBringUpExceptions(){
	SqlConnection sqlconn = SqlConnection.getInstance();
	Cache cache = Cache.getInstance();
	File fl = new File("output.txt");
	try {
	    FileReader fr = new FileReader(fl);
	    BufferedReader br = new BufferedReader(fr);
	    String line = "";
	    int i = 0;
	    while ((line = br.readLine()) != null){
		String[] lineArray = line.split(",");
		int date = Integer.parseInt(lineArray[4]);
		int month = Integer.parseInt(lineArray[5]);
		int year = Integer.parseInt(lineArray[6]);
		ProfileInfo profileInfo = new ProfileInfo(lineArray[1], lineArray[2], lineArray[3], 
			new Day(date, month, year), lineArray[0]);
		
		cache.setVal(lineArray[0], profileInfo);
		System.out.println(++i + "");
		Thread.sleep(10);
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(CacheBasicLRUTest.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(Exception e){
	    e.printStackTrace();
	}
	
    }
    
    @Test
    public void recentlyAddedValueShouldBeOnCache() throws InterruptedException{
	
	System.out.println(ServerSetting.getMaxLRUSize());
	
	cache.setVal(data0.id, data0);
	Thread.sleep(100);
	assertEquals(true, cache.getHashMap().containsKey(data0.id));
	cache.removeVal(data0.id);
	
	Thread.sleep(100);

    }
//    
    @Test
    public void theLeastRecentlyUsedValueShouldBeRemoved() throws InterruptedException{
	cache.setVal(data0.id, data0);
	cache.setVal(data1.id, data1);
	cache.setVal(data2.id, data2);
	cache.getVal(data0.id);
	cache.setVal(data3.id, data3);
	Thread.sleep(100);
	assertEquals(false, cache.getHashMap().containsKey(data1.id));
	cache.removeVal(data0.id);
	cache.removeVal(data2.id);
	cache.removeVal(data3.id);
	Thread.sleep(100);
    }
//    
    @Test
    public void RemovedValueShouldNotBeAccessibleFromCache() throws InterruptedException{
	cache.setVal(data0.id, data0);
	cache.setVal(data1.id, data1);
	Thread.sleep(100);
	cache.removeVal(data0.id);
	assertEquals(false,cache.getHashMap().containsKey(data0.id));
	cache.removeVal(data1.id);
	Thread.sleep(100);
    }
    
    @Test
    public void ValueFetchedFromDatabaseShouldBeOnCache(){
	cache.getVal("3");
	assertEquals(true, cache.getHashMap().containsKey("3"));
    }
    
    
}

