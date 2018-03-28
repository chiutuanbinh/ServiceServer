/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server.handler;

import com.vng.zing.zalotraing.server.Day;
import com.vng.zing.zalotraing.server.ProfileInfo;
import com.vng.zing.zalotraing.server.ProfileService;
import com.vng.zing.zalotraing.server.cache.Cache;
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
	boolean result = Cache.getInstance().setVal(profile.id, profile);
        return result;
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

	boolean result = Cache.getInstance().removeVal(id);

	return result; 
    }
    @Override
    public boolean updateProfile(ProfileInfo profile) throws TException{
	return Cache.getInstance().updateVal(profile);
    }
}
