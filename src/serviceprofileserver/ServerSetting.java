/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author root
 */
public final class ServerSetting {
    private static final ServerSetting INSTANCE = new ServerSetting();
    private static String DB_TypeString = "DB_Type";
    private static String DB_Type = "NoSQL"; 
    private static String NoSQLPoolSizeString = "NoSQLPoolSize";
    private static String NoSQLPoolSize = "5";
    private static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String appConfigPath = rootPath + "app.properties";
    private ServerSetting(){
	
	
	File file = new File(appConfigPath);
	try {
	    if (!file.exists()){

		Properties initSetting = new Properties();
		initSetting.setProperty(DB_TypeString, "NoSQL");
		initSetting.setProperty(NoSQLPoolSizeString, "5");
		initSetting.store(new FileWriter(appConfigPath), "default setting");

	    }
	    Properties propReader = new Properties();
	    propReader.load(new FileInputStream(appConfigPath));
	    DB_Type = propReader.getProperty(DB_TypeString, "NoSQL");
	    NoSQLPoolSize = propReader.getProperty(NoSQLPoolSizeString, "5");
	} catch (Exception e) {
		e.printStackTrace();
	}    
    }
    public static String getDBType(){
	System.out.println(DB_Type);
	return DB_Type;
    }
    public static String getNoSQLPoolSize(){
	return NoSQLPoolSize;
    }
    
    public static ServerSetting getInstance(){
	return INSTANCE;
    }
    
}
