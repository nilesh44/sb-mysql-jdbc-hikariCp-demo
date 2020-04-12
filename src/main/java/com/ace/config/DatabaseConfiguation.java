package com.ace.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
@Component
public class DatabaseConfiguation {
	
	private String url;
	private String driverClass;
	private String userName;
	private String password;
	
 public	DatabaseConfiguation(){
		
	}
	
 public DatabaseConfiguation(String driverClass,String url , String userName, String password) {
		super();
		
		this.url = url;
		this.driverClass = driverClass;
		this.userName = userName;
		this.password = password;
		loadDriverClass();
	}

private  void loadDriverClass(){
	try {
		
		Class.forName(this.driverClass);
	} catch (ClassNotFoundException e) {
		System.out.println("Driver Class notFound");
		e.printStackTrace();
	}
	  System.out.println("MySQL JDBC Driver Registered!");
	}
 
public Connection getConnection(){
	Connection connection=null;
	try {
	connection=DriverManager.getConnection(this.url, this.userName, this.password);
	 System.out.println("SQL Connection to database established!");
	} catch (SQLException e) {
		  System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
	}
	return connection;
 }





}
