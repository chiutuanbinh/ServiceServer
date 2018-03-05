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
import org.apache.thrift.server.TThreadPoolServer;
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
            System.out.println("Starting the server - ready to serve");
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
        StartSimpleServer(new ProfileService.Processor<>(new ProfileServiceHandler()));
    }
    
}
