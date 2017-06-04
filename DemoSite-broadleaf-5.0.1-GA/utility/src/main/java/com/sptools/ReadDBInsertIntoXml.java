package com.sptools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class ReadDBInsertIntoXml {
	Hashtable<String, String> hashtable = new Hashtable<String, String>();

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sptools_web", "root",
					"admin");

			ReadDBInsertIntoXml dbInsertIntoXml = new ReadDBInsertIntoXml();

			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			while (rs.next()) {
				System.out.println("First Table Name ---->  " + rs.getString(3));
				dbInsertIntoXml.getForeignKey(rs.getString(3), connection);

			}
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getForeignKey(String tableName, Connection connection) {
		System.out.println("Main table --> " + tableName);

		try {
			DatabaseMetaData md = connection.getMetaData();
			ResultSet foreignKeys = md.getImportedKeys(connection.getCatalog(), null, tableName);
			while (foreignKeys.next()) {
				String fkTableName = foreignKeys.getString("FKTABLE_NAME");
				System.out.println("fkTableName --> " + fkTableName);
				String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
				String pkTableName = foreignKeys.getString("PKTABLE_NAME");
				String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
				System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);
				if (hashtable.get(pkTableName) == null) {
					hashtable.put(tableName, tableName);
					getForeignKey(pkTableName, connection);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
