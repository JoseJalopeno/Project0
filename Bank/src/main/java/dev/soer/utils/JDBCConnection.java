package dev.soer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.lang.ClassNotFoundException;

public class JDBCConnection {

	/*
	 * to be implemented next week when we go over this
	 */

	private static Connection conn = null;

	// define a method to get teh connection
	public static Connection getConnection() {
		// check if connection does not exist - make one
		try {
			if (conn == null) {
				Class.forName("org.postgresql.Driver"); // hot fix to ensure that the driver loads correctly when the application starts
				//to establish connection we need credentials
				//url(endpoint), username, password
				Properties props = new Properties();
				InputStream input = JDBCConnection.class.getClassLoader().getResourceAsStream("connection.properties");
				props.load(input);
				String url = props.getProperty("url");
				String username = props.getProperty("username");
				String password = props.getProperty("password");
				conn = DriverManager.getConnection(url, username, password);
				
				return conn;
			}
			else {
				return conn;
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
