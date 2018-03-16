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
        return Cache.getInstance().setVal(profile.id, profile);
    }
    
    
    @Override
    public ProfileInfo getProfile(String id) throws TException{
        ProfileInfo result = Cache.getInstance().getVal(id);    

	if (result == null)
	    result = new ProfileInfo("null", "null", "null", new Day(), "null");
	return result;
    }
    
    @Override 
    public boolean removeProfile(String id) throws TException{
	return Cache.getInstance().removeVal(id);
    }
    @Override
    public boolean updateProfile(ProfileInfo profile) throws TException{
	return Cache.getInstance().updateVal(profile);
    }
}
