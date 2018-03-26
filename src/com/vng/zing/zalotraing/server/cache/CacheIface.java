/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server.cache;

import com.vng.zing.zalotraing.server.ProfileInfo;

/**
 *
 * @author root
 */
public interface CacheIface {
    public boolean setVal(String key, ProfileInfo element);
    public ProfileInfo getVal(String key);
    public boolean removeVal(String key);
    public boolean updateVal(ProfileInfo element);
}
