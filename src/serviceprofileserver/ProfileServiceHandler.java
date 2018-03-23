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
	boolean result = CacheType1.getInstance().setVal(profile.id, profile);
        return result;
    }
    
    
    @Override
    public ProfileInfo getProfile(String id) throws TException{
        ProfileInfo result = CacheType1.getInstance().getVal(id);    
	if (result == null)
	    result = new ProfileInfo("null", "null", "null", new Day(), "null");
	return result;
    }
    
    @Override 
    public boolean removeProfile(String id) throws TException{

	boolean result = CacheType1.getInstance().removeVal(id);

	return result; 
    }
    @Override
    public boolean updateProfile(ProfileInfo profile) throws TException{
	return CacheType1.getInstance().updateVal(profile);
    }
}
