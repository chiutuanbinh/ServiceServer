/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server.database;

import com.vng.zing.zalotraing.server.ProfileInfo;
import com.vng.zing.zalotraing.server.ServerSetting;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author root
 */
public class DBHelper implements DBHelperIface{
    private final String DBSetting = ServerSetting.getDBType();
    private ExecutorService executor = Executors.newFixedThreadPool(100);
    private final NoSQLConnection noSQLConnection = NoSQLConnection.getInstance();
    private final SqlConnection sqlConnection = SqlConnection.getInstance();
    
    private static DBHelper INSTANCE = new DBHelper();
    private DBHelper(){
	
    }
    
    public static DBHelper getInstance(){
	return INSTANCE;
    }
    
    @Override
    public boolean setDB(ProfileInfo profile) {
	SyncToDB setTask = new SyncToDB(0, profile, profile.id);
	executor.submit(setTask);
	return true;
    }

    @Override
    public ProfileInfo getDB(String id) {
	ProfileInfo result = noSQLConnection.getFromBD(id);
	return result;
    }

    @Override
    public boolean removeDB(String id) {
	SyncToDB removeTask = new SyncToDB(2, null, id);
	executor.submit(removeTask);
	return true;
    }
    
    private class SyncToDB implements Runnable{
	int opType;
	ProfileInfo data;
	String dataKey;
	
	public SyncToDB(int caller, ProfileInfo inputData, String inputKey){
	    this.opType = caller;
	    this.data = inputData;
	    this.dataKey = inputKey;
	}
	
	@Override
	public void run(){
	    //System.out.println("DataBase Sync Thread Start");
	    switch (this.opType){
	    case 0:// save operation
		if (DBSetting.equals("NoSQL")){
		    noSQLConnection.saveToDB(data);
		}
		else if (DBSetting.equals("MySQL")){
		    sqlConnection.saveToDB(data);
		}
		break;
	    case 1: // update operation
		if (DBSetting.equals("NoSQL")){
		    noSQLConnection.updateToDB(data);
		}
		else if (DBSetting.equals("MySQL")){
		    sqlConnection.updateToDB(data);
		}
		break;
	    case 2: // remove operation
		if (DBSetting.equals("NoSQL")){
		    noSQLConnection.removeFromDB(dataKey);
		}
		else if (DBSetting.equals("MySQL")){
		    sqlConnection.removeFromDB(dataKey);
		}
		
	    }
	    
	}
    }
    
}
