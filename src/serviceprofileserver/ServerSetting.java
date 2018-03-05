/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

/**
 *
 * @author root
 */
public final class ServerSetting {
    private static final ServerSetting INSTANCE = new ServerSetting();
    private static String DB_Type = "NoSQL"; 
    private ServerSetting(){
	String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	String appConfigPath = rootPath + "setting.properties";
	File file = new File(appConfigPath);
	if (!file.exists()){
	    try {
		Properties initSetting = new Properties();
		initSetting.setProperty("DB_Type", "NoSQL");
		initSetting.store(new FileWriter(appConfigPath), "default setting");
	    } catch (Exception e) {
		e.printStackTrace();
	    }    
	}
	else {
	    Properties propReader = new Properties();
	    DB_Type = propReader.getProperty("DB_Type", "NoSQL");
	}
    }
    public static String getDBType(){
	return DB_Type;
    }
    
}
