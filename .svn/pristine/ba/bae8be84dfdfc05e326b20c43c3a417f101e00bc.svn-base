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
import org.apache.commons.dbcp2.BasicDataSource;
import sun.security.jca.GetInstance;

/**
 *
 * @author root
 */
public final class SqlConnection {
    private static final int INIT_CONNECTION = ServerSetting.getConnectionPoolSize();
    private static final BasicDataSource DATA_SOURCE ;
    static {
	DATA_SOURCE =  new BasicDataSource();
	String url = ServerSetting.getSqlUrl();
        String username = ServerSetting.getSqlUsername();
        String password = ServerSetting.getSqlPassword();
	String driverClassName = ServerSetting.getSqlDriverClassName();
        
        DATA_SOURCE.setUrl(url);
	DATA_SOURCE.setDriverClassName(driverClassName);
	DATA_SOURCE.setUsername(username);
	DATA_SOURCE.setPassword(password);
	DATA_SOURCE.setInitialSize(INIT_CONNECTION);
	
	System.out.println("SQL connections established");
    }
    
    private static final String NAME = "name";
    private static final String MAIL = "email";
    private static final String PHONE = "phoneNumber";
    private static final String ID = "id";
    private static final String BDAY = "birthDay";
    private static final SqlConnection INSTANCE = new SqlConnection();
    
    
    
    
    
    private SqlConnection(){
        
    }
    
    private Connection getConnection()throws SQLException{
	return DATA_SOURCE.getConnection();
    }
    //save the item to the DB
    public boolean saveToDB(ProfileInfo saveItem){
        try (Connection connection = this.getConnection();
		Statement stmt = connection.createStatement()){

	    Day saveItemDay = saveItem.birthday;
	    String date = Integer.toString(saveItemDay.date);
	    String month = Integer.toString(saveItemDay.month);
	    String year = Integer.toString(saveItemDay.year);
	    String sqlQuery = "INSERT INTO userProfile VALUES (\'" +
                    saveItem.id + "\',\'" + saveItem.userName + "\',\'" + 
                    saveItem.email + "\',\'" + saveItem.phoneNumber + "\',\'" +
                    year + "-"+ 
                    month + "-" + 
                    date +"\');";
	    
            stmt.executeUpdate(sqlQuery);
        }
        catch(SQLException e) {
            //e.printStackTrace();
	    return false;
        }
        return true;
    }
    //load the item from the DB
    public ProfileInfo getFromDB(String key) {
	
        ProfileInfo returnValue = null;
	try (Connection connection = this.getConnection();
		Statement stmt = connection.createStatement()
		){
	    ResultSet rs;
	    rs = stmt.executeQuery("SELECT * FROM userProfile WHERE id =\'" + key+"\';");
	    if (!rs.next())
		return null;
	    returnValue = new ProfileInfo(rs.getString(NAME), 
		rs.getString(MAIL), rs.getString(PHONE), 
		new Day(rs.getDate(BDAY).getDate(), 
			rs.getDate(BDAY).getMonth(),
			rs.getDate(BDAY).getYear()), 
		rs.getString(ID));
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	//System.out.println(returnValue.toString());
	CacheType1.getInstance().syncCache(key, returnValue, 2);
	return returnValue;
        
    }
    //update the item in the DB 
    public boolean updateToDB(ProfileInfo updateItem){
	try (Connection connection = this.getConnection();
		Statement stmt = connection.createStatement()){
	    Day updateItemDay = updateItem.birthday;
	    String date = Integer.toString(updateItemDay.date);
	    String month = Integer.toString(updateItemDay.month);
	    String year = Integer.toString(updateItemDay.year);
	    String updateSQL = "UPDATE userProfile "
		    + "SET id = \'" + updateItem.id 
		    + "\', name = \'" + updateItem.userName
		    + "\', email = \'" + updateItem.email
		    + "\', phoneNumber = \'" + updateItem.phoneNumber
		    + "\', birthDay =\'" + year + "-" + month + "-" + date + "\';";
		    
	    stmt.executeUpdate(updateSQL);
	    CacheType1.getInstance().syncCache(year, updateItem, 1);
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
    //remove the item from the DB
    public boolean removeFromDB(String key){
	try (Connection connection = this.getConnection();
		Statement stmt = connection.createStatement()){
	    String deleteSQL = "DELETE FROM userProfile "
		    + "WHERE id = \'" + key + "\';";
	    stmt.executeUpdate(deleteSQL);
	    CacheType1.getInstance().syncCache(key, null, 0);
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
    
    
    public static SqlConnection getInstance(){
        return INSTANCE;
    }
    @Override
    protected void finalize() throws Throwable{
        try {
            DATA_SOURCE.close();
        }
        finally{
            super.finalize();
        }
    }
}
