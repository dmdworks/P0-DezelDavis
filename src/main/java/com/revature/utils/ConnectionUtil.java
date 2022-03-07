package com.revature.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton utility for creating and retrieving database connection
 */
public class ConnectionUtil {
	private static ConnectionUtil cu = null;
	private static Properties prop;
	
	/**
	 * This method should read in the "database.properties" file and load
	 * the values into the Properties variable
	 */
	private ConnectionUtil() {
		File fs = new File("C:\\Users\\Dezel\\Desktop\\Revature Projects\\P0-DezelDavis\\src\\main\\resources\\database.properties");
		FileReader fr;
		try {
			fr = new FileReader(fs);
			prop = new Properties();
			prop.load(fr);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static synchronized ConnectionUtil getConnectionUtil() {
		if(cu == null)
			cu = new ConnectionUtil();
		return cu;
	}
	
	/**
	 * This method should create and return a Connection object
	 * @return a Connection to the database
	 */
	public Connection getConnection() {
		// Hint: use the Properties variable to setup your Connection object
		try {
			Connection con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("usr"), prop.getProperty("pswd"));
			return con;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
