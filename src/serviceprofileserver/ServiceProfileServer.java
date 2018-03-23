/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 *
 * @author root
 */
public class ServiceProfileServer {
    
    
    public static void StartSimpleServer(ProfileService.Processor<ProfileServiceHandlerWithTimeMeasurer> processor){
        
        try {
            TServerTransport serverTransport = new TServerSocket(9696);
            TServer server = new TSimpleServer(
                    new TServer.Args(serverTransport).processor(processor));
            System.out.println("Starting a Simple Server - ready");
            server.serve();
	    
        }
        catch (TException e){
            e.printStackTrace();
        }
        
    }
    
    public static void StartNonBlockingServer(ProfileService.Processor<ProfileServiceHandlerWithTimeMeasurer> processor){
	try {
	    TNonblockingServerTransport NBTransport = new TNonblockingServerSocket(9696);
	    TServer server = new TNonblockingServer(new TNonblockingServer.Args(NBTransport).processor(processor));
	    System.out.println("Starting a Nonblocking Server - ready");
	    server.serve();
	}
	catch (TException e){
		e.printStackTrace();
	}
    }
    
    public static void StartThreadedSelectorServer(ProfileService.Processor<ProfileServiceHandlerWithTimeMeasurer> processor){
	try {
	    TNonblockingServerTransport TSTransport = new TNonblockingServerSocket(9696);
	    TServer server = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(TSTransport).processor(processor));
	    System.out.println("Starting a Threaded Selector Server - ready");
	    server.serve();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    public static void StartThreadPoolServer(ProfileService.Processor<ProfileServiceHandlerWithTimeMeasurer> processor){
	try {
	    TServerTransport transport = new TServerSocket(9696);
	    TThreadPoolServer server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
	    server.serve();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    static AtomicLong totalGetTime = new AtomicLong(1);
    static AtomicLong totalSetTime = new AtomicLong(1);
    static AtomicLong totalRemoveTime = new AtomicLong(1);
    static AtomicLong getReq = new AtomicLong(0);
    static AtomicLong setReq = new AtomicLong(0);
    static AtomicLong removeReq = new AtomicLong(0);
    static AtomicLong lastGetTime = new AtomicLong(0);
    static AtomicLong lastSetTime = new AtomicLong(0);
    static AtomicLong lastRemoveTime = new AtomicLong(0);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
	
	//start DB Connections
	SqlConnection.getInstance();
	NoSQLConnection.getInstance();
	Measurer pMeasurer = new Measurer();
	pMeasurer.start();
//        StartSimpleServer(new ProfileService.Processor<>(new ProfileServiceHandlerWithTimeMeasurer()));
//	StartNonBlockingServer(new ProfileService.Processor<>(new ProfileServiceHandlerWithTimeMeasurer()));
	StartThreadedSelectorServer(new ProfileService.Processor<>(new ProfileServiceHandlerWithTimeMeasurer()));
//	StartThreadPoolServer(new ProfileService.Processor<>(new ProfileServiceHandlerWithTimeMeasurer()));
    }

    public static class Measurer extends Thread{
	public Measurer(){
	}

	@Override
	public void run() {
	    while(true){
		try {
		    sleep(30000);
		} catch (InterruptedException ex) {
		    Logger.getLogger(ServiceProfileServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("######################################");
		System.out.println("SetReq = " + setReq);
		System.out.println("TotalTimeSet = " + totalSetTime);
		System.out.println("LastProcTime = " + lastSetTime);
		System.out.println("AverageProcRate = " + setReq.get()/(totalSetTime.get()));

		System.out.println("GetReq = " + getReq);
		System.out.println("TotalTimeGet = " + totalGetTime);
		System.out.println("LastProcTime = " + lastGetTime);
		System.out.println("AverageProcRate = " + getReq.get()/(totalGetTime.get()));

		System.out.println("RemvReq = " + removeReq);
		System.out.println("TotalTimeRemove = " + totalRemoveTime);
		System.out.println("LastProcTime = " + lastRemoveTime);
		System.out.println("AverageTimeProcRate = " + removeReq.get()/(totalRemoveTime.get()));
	    }
	}
	
    }
    
}
