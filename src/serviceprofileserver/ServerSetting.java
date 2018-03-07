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
    private String DB_Type = "NoSQL"; 
    private ServerSetting(){
	
	String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
String appConfigPath = rootPath + "app.properties";
	File file = new File(appConfigPath);
	try {
	    if (!file.exists()){

		Properties initSetting = new Properties();
		initSetting.setProperty("DB_Type", "NoSQL");
		initSetting.store(new FileWriter(appConfigPath), "default setting");

	    }
	    Properties propReader = new Properties();
	    propReader.load(new FileInputStream(appConfigPath));
	    this.DB_Type = propReader.getProperty("DB_Type", "NoSQL");
	} catch (Exception e) {
		e.printStackTrace();
	}    
    }
    public String getDBType(){
	System.out.println(this.DB_Type);
	return this.DB_Type;
    }
    public static ServerSetting getInstance(){
	return INSTANCE;
    }
    
}
