/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import sun.security.jca.GetInstance;

/**
 *
 * @author root
 */
public final class sqlConnection {
    private static final int MAX_CONNECTION = 10;
    
    private static final sqlConnection INSTANCE = new sqlConnection();
    
    private Connection connection;
    
    private sqlConnection(){
        String url = "jdbc:mysql://localhost:3306/javabase";
        String username = "java";
        String password = "123456";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } 
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        }       
        catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    
    public boolean saveToDB(profileInfo saveItem){
        try{
	    Statement stmt = connection.createStatement();
        
	    day saveItemDay = saveItem.birthday;
	    String date = Integer.toString(saveItemDay.date);
	    String month = Integer.toString(saveItemDay.month);
	    String year = Integer.toString(saveItemDay.year);
            stmt.executeUpdate("INSERT INTO userProfile VALUES (\'" +
                    saveItem.id + "\',\'" + saveItem.userName + "\',\'" + 
                    saveItem.email + "\',\'" + saveItem.phoneNumber + "\',\'" +
                    year + "-"+ 
                    month + "-" + 
                    date +"\');");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public profileInfo getFromDB(String key) {
	
        profileInfo returnValue = null;
	try {
	    ResultSet rs;
	    Statement stmt;
	
	    stmt = connection.createStatement();
	    rs = stmt.executeQuery("SELECT * FROM userProfile WHERE id =\'" + key+"\';");
	    
	    if (!rs.next())
		return null;
	    returnValue = new profileInfo(rs.getString("name"), 
		rs.getString("email"), rs.getString("phoneNumber"), 
		new day(rs.getDate("birthDay").getDate(), 
			rs.getDate("birthDay").getMonth(),
			rs.getDate("birthDay").getYear()), 
		rs.getString("id"));

	} catch (Exception e) {
	    e.printStackTrace();
	}
	//System.out.println(returnValue.toString());
	return returnValue;
        
    }
    public static sqlConnection getInstance(){
        return INSTANCE;
    }
    @Override
    protected void finalize() throws Throwable{
        try {
            connection.close();
        }
        finally{
            super.finalize();
        }
    }
}
