/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import serviceprofileserver.CacheType1;
import serviceprofileserver.Day;
import serviceprofileserver.NoSQLConnection;
import serviceprofileserver.ProfileInfo;
import serviceprofileserver.ProfileServiceHandlerWithTimeMeasurer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class PerformanceTestNoCache {
    static int threadCount = 3;
    static AtomicLong totalGetTime = new AtomicLong(0);
    static AtomicLong totalSetTime = new AtomicLong(0);
    static AtomicLong totalRemoveTime = new AtomicLong(0);
    static AtomicLong getReq = new AtomicLong(0);
    static AtomicLong setReq = new AtomicLong(0);
    static AtomicLong removeReq = new AtomicLong(0);
    static AtomicLong lastGetTime = new AtomicLong(0);
    static AtomicLong lastSetTime = new AtomicLong(0);
    static AtomicLong lastRemoveTime = new AtomicLong(0);
    static BufferedReader br = null;
    static {
	File fl = new File("output.txt");
	try {
	    FileReader fr = new FileReader(fl);
	    br = new BufferedReader(fr);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    ProfileServiceHandlerWithTimeMeasurer processor = new ProfileServiceHandlerWithTimeMeasurer();
    CacheType1 cache = CacheType1.getInstance();
    NoSQLConnection dbConn = NoSQLConnection.getInstance();
    public PerformanceTestNoCache() {
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
    class RequestToDB extends Thread{
	private int index;
	private int requestCount;
	public RequestToDB(int i, int count){
	    index = i;
	    requestCount = count;
	}
	
	@Override
	public void run(){
	    try {
		for (int i = 0; i < requestCount; i++){
		    if (index == 1)
			System.out.println(i + "/ " + requestCount + " " + index);
		    String line = null;
		    if ((line = br.readLine()) == null){
			System.out.println("gohere?");
			break;
		    }
		    //System.out.println(line);
		    String[] lineArray = line.split(",");
		    
		    if (i % index == 2){
			long start = System.nanoTime();
			ProfileInfo pi = dbConn.getFromBD(lineArray[0]);
			lastGetTime.set(System.nanoTime() - start);
			totalGetTime.addAndGet(lastGetTime.get());
			getReq.addAndGet(1);
			
		    }
		    if (i % index == 1){
			Day d = new Day(Integer.parseInt(lineArray[4]),
				Integer.parseInt(lineArray[5]),
				Integer.parseInt(lineArray[6]));
			ProfileInfo profile = new ProfileInfo(lineArray[1],
				lineArray[2],
				lineArray[3],
				d, lineArray[0]);
			long start = System.nanoTime();
			dbConn.saveToDB(profile);
			lastSetTime.set(System.nanoTime() - start);
			totalSetTime.addAndGet(lastSetTime.get());
			setReq.addAndGet(1);
		    }
		    if (i % index == 0){
			long start = System.nanoTime();
			dbConn.removeFromDB(lineArray[0]);
			lastRemoveTime.set(System.nanoTime() - start);
			totalRemoveTime.addAndGet(lastRemoveTime.get());
			removeReq.addAndGet(1);
		    }
		}
		    
	    } catch (Exception e) {
	    }
	}
    }
    @Test
    public void RequestDirectlyToDB(){
	List<Thread> threadList = new LinkedList<>(); 
	for (int i = 0; i < 3; i ++){
	    RequestToDB req = new RequestToDB(i+1, 30000);
	    threadList.add(req);
	    req.start();
	}
	for (Thread thread : threadList){
	    try {
		thread.join();
	    } catch (InterruptedException ex) {
		Logger.getLogger(PerformanceTestWithCache.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	

	
	
	System.out.println("SetReq = " + setReq);
	System.out.println("TotalTimeSet = " + totalSetTime);
	System.out.println("LastProcTime = " + lastSetTime);
	System.out.println("AverageProcRate = " + setReq.get()*1000000/(totalSetTime.get()));
	
	System.out.println("GetReq = " + getReq);
	System.out.println("TotalTimeGet = " + totalGetTime);
	System.out.println("LastProcTime = " + lastGetTime);
	System.out.println("AverageProcRate = " + getReq.get()*1000000/(totalGetTime.get()));
	
	System.out.println("RemvReq = " + removeReq);
	System.out.println("TotalTimeRemove = " + totalRemoveTime);
	System.out.println("LastProcTime = " + lastRemoveTime);
	System.out.println("AverageTimeProcRate = " + removeReq.get()*1000000/(totalRemoveTime.get()));
    }
}
