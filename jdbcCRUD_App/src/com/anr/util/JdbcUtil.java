package com.anr.util;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

	//creating constructor as private so that nobody can create an instance/object and use this. Then how do we use it? - Directly using class name because all the methods are static.
	private JdbcUtil() {}

	//loading the driver which happens automatically - wrote the code as a reference to see what happens in the background.
	//step 1 - Loading and   the Driver.
	
	/*static {
		try {
			Class.forName("com.musql.cj.jdbc.Driver");
		} catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}*/
	
	public static Connection getJdbcConnection() throws Exception {
		
		//Read data(url, username,password) from application.properties file
		
		FileInputStream fis = new FileInputStream("C:\\Users\\ajayr\\eclipse-Projects\\jdbcCRUD App\\src\\com\\anr\\properties\\application.properties");
		Properties properties = new Properties();
		properties.load(fis);
		
		
		//step 1 - Establish the connection
		/*
		 * As we are using properties file this data here is no longer needed
		String url = "jdbc:mysql://localhost:3306/newdb";
		String username = "root";
		String password = "1234";
		*/
		
		Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"),properties.getProperty("password")); 

		//System.out.println("Connection Created");

		return connection;
	}

	public static void closingResourses(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {

		//step 6 - Closing Resourses

		if(connection != null)
			connection.close();
			
		if (statement != null)
			statement.close();

		if (resultSet != null)
			resultSet.close();
		
		//System.out.println("Resourses closed..");

	}

}