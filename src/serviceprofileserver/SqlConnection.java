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
public final class SqlConnection {
    private static final int MAX_CONNECTION = 10;
    
    private static final SqlConnection INSTANCE = new SqlConnection();
    
    private Connection connection;
    
    private SqlConnection(){
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
    //save the item to the DB
    public boolean saveToDB(ProfileInfo saveItem){
        try{
	    Statement stmt = connection.createStatement();
        
	    Day saveItemDay = saveItem.birthday;
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
    //load the item from the DB
    public ProfileInfo getFromDB(String key) {
	
        ProfileInfo returnValue = null;
	try {
	    ResultSet rs;
	    Statement stmt;
	
	    stmt = connection.createStatement();
	    rs = stmt.executeQuery("SELECT * FROM userProfile WHERE id =\'" + key+"\';");
	    
	    if (!rs.next())
		return null;
	    returnValue = new ProfileInfo(rs.getString("name"), 
		rs.getString("email"), rs.getString("phoneNumber"), 
		new Day(rs.getDate("birthDay").getDate(), 
			rs.getDate("birthDay").getMonth(),
			rs.getDate("birthDay").getYear()), 
		rs.getString("id"));

	} catch (Exception e) {
	    e.printStackTrace();
	}
	//System.out.println(returnValue.toString());
	return returnValue;
        
    }
    //update the item in the DB 
    //TODO: implement
    public boolean updateToDB(ProfileInfo updateItem){
	return true;
    }
    //remove the item from the DB
    //TODO: implement
    public boolean removeFromDB(String key){
	return true;
    }
    
    public static SqlConnection getInstance(){
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
