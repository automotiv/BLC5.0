package com.sptools.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class TableData {
	
	public static void main(String[] args) {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sptools_web", "root",
					"admin");
			Class.forName("com.mysql.jdbc.Driver");
			
			TableData data = new TableData();
			data.getTableData(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public  void getTableData(Connection connection) {
		try {
			
			System.out.println("Table Data");
			Statement createStatement = connection.createStatement();
			ResultSet executeQuery = createStatement.executeQuery("select * from sptools_web.BLC_ADDITIONAL_OFFER_INFO;");
			ResultSetMetaData metaData = executeQuery.getMetaData();
			int i = 0;
			
			while (executeQuery.next()) {
				
				while (i < metaData.getColumnCount()) {
					i++;
					System.out.println(executeQuery.getString(metaData.getColumnName(i)));
				}				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
