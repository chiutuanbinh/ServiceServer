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
    public boolean setProfile(ProfileInfo profile) throws TException{
	long start = System.nanoTime();
	boolean result = Cache.getInstance().setVal(profile.id, profile);
	long interval = System.nanoTime()- start;
	ServiceProfileServer.lastSetTime.set(interval);
	ServiceProfileServer.totalSetTime.addAndGet(interval);
	ServiceProfileServer.setReq.addAndGet(1);
        return result;
    }
    
    
    @Override
    public ProfileInfo getProfile(String id) throws TException{
	long start = System.nanoTime();
        ProfileInfo result = Cache.getInstance().getVal(id);    
	long interval = System.nanoTime()- start;
	ServiceProfileServer.lastGetTime.set(interval);
	ServiceProfileServer.totalGetTime.addAndGet(interval);
	ServiceProfileServer.getReq.addAndGet(1);
	if (result == null)
	    result = new ProfileInfo("null", "null", "null", new Day(), "null");
	return result;
    }
    
    @Override 
    public boolean removeProfile(String id) throws TException{
	long start = System.nanoTime();
	boolean result = Cache.getInstance().removeVal(id);
	long interval = System.nanoTime()- start;
	ServiceProfileServer.lastRemoveTime.set(interval);
	ServiceProfileServer.totalRemoveTime.addAndGet(interval);
	ServiceProfileServer.removeReq.addAndGet(1);
	return result; 
    }
    @Override
    public boolean updateProfile(ProfileInfo profile) throws TException{
	return Cache.getInstance().updateVal(profile);
    }
}
