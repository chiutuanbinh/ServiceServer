/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import org.apache.thrift.TException;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 *
 * @author root
 */
public class ServiceProfileServer {

    
    public static void StartSimpleServer(ProfileService.Processor<ProfileServiceHandler> processor){
        
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
    
    public static void StartNoBlockingServer(ProfileService.Processor<ProfileServiceHandler> processor){
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
	
	//start DB Connections
	SqlConnection.getInstance();
	NoSQLConnection.getInstance();
        //StartSimpleServer(new ProfileService.Processor<>(new ProfileServiceHandler()));
	StartNoBlockingServer(new ProfileService.Processor<>(new ProfileServiceHandler()));
    }
    
}
