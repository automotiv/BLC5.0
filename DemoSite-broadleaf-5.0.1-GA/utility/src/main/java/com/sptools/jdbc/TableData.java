package com.sptools.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TableData {

	public static void main(String[] args) {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sptools_web", "root", "admin");
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

	public void getTableData(Connection connection) {
		try {

			System.out.println("Table Data");
			Vector<String> columnNames = new Vector<String>();
			Statement createStatement = connection.createStatement();
			ResultSet executeQuery = createStatement.executeQuery("select * from sptools_web.BLC_ADDRESS;");
			ResultSetMetaData metaData = executeQuery.getMetaData();
			int i = 0;
			System.out.println(executeQuery.getFetchSize());
			StringBuilder template = new StringBuilder();
			template.append("INSERT INTO " + "sptools_web.BLC_ADDRESS" + " (");
			int k = 0;
			while (k < metaData.getColumnCount()) {
				k++;
				System.out.print(metaData.getColumnName(k) + "\t");
				columnNames.add(metaData.getColumnName(k));
			}
			for (int j = 0; j < columnNames.size(); j++) {
				if (j == columnNames.size() - 1) {
					template.append(columnNames.get(j));
				} else {
					template.append(columnNames.get(j) + ", ");
				}

			}
			template.append(" ) VALUES ( ");
			while (executeQuery.next()) {
				System.out.println("In for loop");
				/*
				 * while (i < metaData.getColumnCount()) { i++;
				 * System.out.println(executeQuery.getString(metaData.
				 * getColumnName(i))); }
				 */
				while (i < metaData.getColumnCount()) {
					i++;
					if (i == columnNames.size()) {
						template.append(executeQuery.getString(metaData.getColumnName(i)));
					} else {
						template.append(executeQuery.getString(metaData.getColumnName(i)) + ", ");
					}
				}
			}
			template.append(" )");
			System.out.println(template.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
