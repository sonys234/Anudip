package com.bookvault.utility;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbConnection {
	private static Connection con = null;

	public static Connection establishConnection() {

		try {
			//step 1: Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//step 2: Establish the connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookVault", "user_name", "password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String[] args) {
		Connection con = DbConnection.establishConnection();
		if (con != null)
			System.out.println("Connected");
		else
			System.out.println("Not connected..");

	}


}
