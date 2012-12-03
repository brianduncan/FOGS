package edu.sjsu.cmpe297.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONObject;

public class FogsDatabase {

	private final static String DB_URL = "jdbc:mysql://%s:%s/%s";
	
    protected Connection con;
    
    /**
     * Returns appropriate database connection for
     * the running environment.
     * 
     * @return Connection
     */
	protected Connection getDatabaseConnection() {
		
		// Are we deployed in the cloud?
		if (deployedInCloud()) {
			
			return getCloudConnection();
		} else {
			
			return getLocalConnection();
		}
	}
	
	/**
	 * Returns true if we are in the cloud environment,
	 * false if we are not.
	 * 
	 * @return boolean
	 */
	private boolean deployedInCloud() {
		
		// Check for VCAP environment variable
		if (System.getenv("VCAP_SERVICES") == null) {
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Creates a properly formatted database connection string
	 * 
	 * @param hostname
	 * @param port
	 * @param database
	 * @return
	 */
	private String createDsn(String hostname, String port, String database) {
		
		return String.format(DB_URL, hostname, port, database);
	}
	
	/**
	 * Returns a connection to a database instance on 
	 * cloudfoundry.
	 * 
	 * @return Connection
	 */
	private Connection getCloudConnection() {
				
		JSONObject serviceObject;
		JSONObject credentials;
		String url = "";
		String services = System.getenv("VCAP_SERVICES");
		
		try {
			// Create objects that hold dynamic connection properties
		    serviceObject = new JSONObject(services);
		    credentials = serviceObject.getJSONArray("mysql-5.1").getJSONObject(0).getJSONObject("credentials");
			
		    System.err.println("User: " + credentials.getString("user") + ", Pass: " + credentials.getString("password"));
		    
		    // Create a database connection string
			url = createDsn(credentials.getString("hostname"), credentials.getString("port"), credentials.getString("name"));
		    
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection(url, credentials.getString("user"), credentials.getString("password"));
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	/**
	 * Returns a connection to a local/test database
	 * 
	 * @return Connection
	 */
	private Connection getLocalConnection() {
		
		try {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://luxor.svl.ibm.com:3306/cmpe297", "cmpe297", "cmpe297");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
}
