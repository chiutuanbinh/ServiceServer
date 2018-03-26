/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server.database;

import com.vng.zing.zalotraing.server.ProfileInfo;

/**
 *
 * @author root
 */
public interface DBHelperIface {
    public boolean setDB(ProfileInfo profile);
    public ProfileInfo getDB(String id);
    public boolean removeDB(String id);
    
}
