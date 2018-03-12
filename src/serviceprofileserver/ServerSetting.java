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
public final class  ServerSetting {
    
    private static String dBTypeString = "DB_Type";
    private static String dBType = "NoSQL"; 
    
    private static String connectionPoolSizeString = "ConnectionPoolSize";
    private static String connectionPoolSize = "5";
    
    private static String maxLRUSizeString = "LRULimit";
    private static String maxLRUSize = "20";
    
    private static String sqlUrlString = "Url";
    private static String sqlUrl = "jdbc:mysql://localhost:3306/javabase?autoReconnect=true&useSSL=false";
    
    private static String sqlUsernameString = "Username";
    private static String sqlUsername = "java";
    
    private static String sqlPasswordString = "Password";
    private static String sqlPassword = "123456";
    
    private static String sqlDriverClassNameString = "DriverClassName";
    private static String sqlDriverClassName = "com.mysql.jdbc.Driver";
    
    
    private static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String appConfigPath = rootPath + "app.properties";
    private ServerSetting(){
	
	
	File file = new File(appConfigPath);
	try {
	    if (!file.exists()){

		Properties initSetting = new Properties();
		initSetting.setProperty(dBTypeString, dBType);
		initSetting.setProperty(connectionPoolSizeString, connectionPoolSize);
		initSetting.setProperty(maxLRUSizeString, maxLRUSize);
		initSetting.setProperty(sqlUrlString, sqlUrl);
		initSetting.setProperty(sqlUsernameString, sqlUsername);
		initSetting.setProperty(sqlPasswordString, sqlPassword);
		initSetting.setProperty(sqlDriverClassNameString, sqlDriverClassName);
		initSetting.store(new FileWriter(appConfigPath), "default setting");
	    }
	    Properties propReader = new Properties();
	    propReader.load(new FileInputStream(appConfigPath));
	    dBType = propReader.getProperty(dBTypeString, dBType);
	    connectionPoolSize = propReader.getProperty(connectionPoolSizeString, connectionPoolSize);
	    maxLRUSize = propReader.getProperty(maxLRUSizeString,maxLRUSize);
	    sqlUrl = propReader.getProperty(sqlUrlString,sqlUrl);
	    sqlUsername = propReader.getProperty(sqlUsernameString,sqlUsername);
	    sqlPassword = propReader.getProperty(sqlPasswordString,sqlPassword);
	    sqlDriverClassName = propReader.getProperty(sqlDriverClassNameString, sqlDriverClassName);
	} catch (Exception e) {
		e.printStackTrace();
	}    
    }
    public static String getDBType(){
	return dBType;
    }
    public static int getConnectionPoolSize(){
	return Integer.parseInt(connectionPoolSize);
    }
    public static int getMaxLRUSize(){
	return Integer.parseInt(maxLRUSize);
    }
    public static String getSqlUrl(){
	return sqlUrl;
    }
    public static String getSqlUsername(){
	return sqlUsername;
    }
    public static String getSqlPassword(){
	return sqlPassword;
    }
    public static String getSqlDriverClassName(){
	return sqlDriverClassName;
    }

}
