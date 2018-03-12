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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import serviceprofileserver.Day;
import serviceprofileserver.HashTable;
import serviceprofileserver.NoSQLConnection;
import serviceprofileserver.ProfileInfo;
import serviceprofileserver.ServerSetting;
import serviceprofileserver.SqlConnection;

/**
 *
 * @author root
 */
public class JUnitTest {
    
    public JUnitTest() {
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
    public void loadingProperties(){
	System.out.println(ServerSetting.getDBType());
	System.out.println(ServerSetting.getConnectionPoolSize());
    }
    
    
    @Test
    public void addData(){
	SqlConnection sqlconn = SqlConnection.getInstance();
	HashTable cache = HashTable.getInstance();
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
	    Logger.getLogger(JUnitTest.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(Exception e){
	    e.printStackTrace();
	}
	
    }
}

