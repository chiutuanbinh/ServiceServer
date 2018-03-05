/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class ProfileServiceHandler implements ProfileService.Iface{
    
    @Override
    public boolean setProfile(profileInfo profile) throws TException{
        boolean success = false;
	
	
	
	
        int savingOption = 1;
        switch (savingOption){
            case 0: //using hash table
                HashTable.getInstance().setVal(profile.id, profile);
                success = true;
                break;
            case 1: //using mysql
                sqlConnection.getInstance().saveToDB(profile);
		success = true;
                break;
            case 2: //using nosql
                noSQLConnection.getInstance().saveToDB(profile);
                success = true;
                break;
            default:
        }
        //System.out.println(ServiceProfileServer.infoHashtable);
        return success;
    }
    
    
    @Override
    public profileInfo getProfile(String id) throws TException{
        profileInfo result = new profileInfo("null", "null", "null", new day(), "null");
	try{
	    result = HashTable.getInstance().getVal(id);    
	}
	catch(Exception e){
	    e.printStackTrace();
	}
	return result;
    }
}
