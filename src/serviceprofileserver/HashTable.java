/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author root
 */
public final class HashTable {
    private static final HashTable INSTANCE = new HashTable(20);
    private Hashtable<String,profileInfo> infoTable ;
    private Queue<String> LRUQueue;
    
    private HashTable(int i){
        infoTable = new Hashtable<String, profileInfo>(i);
        infoTable.put("1", new profileInfo("A", "A@abc", "922323", new day(12, 12, 1983), "1"));
        infoTable.put("2", new profileInfo("B", "B@abc", "345678", new day(1, 1, 1888), "2"));
	LRUQueue = new LinkedBlockingQueue<String>(30);
    }
    
    public static HashTable getInstance(){
        return INSTANCE;
    }
    //TODO: implement
    public boolean setVal(String key, profileInfo element){
	return true;
    }
    //TODO: implement
    public profileInfo getVal(String key){
	return this.infoTable.get(key);
    }
}
