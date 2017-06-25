package com.sptools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class Reflection {
	
	Hashtable<String, String> hashtable = new Hashtable<String, String>();
	
	public static void main(String[] args) {
		
	}
	
	
	public String getForeignKey(String tableName, Connection connection) {
		System.out.println("Main table --> " + tableName);

		try {
			Vector<String> columnNames = new Vector<String>();
			
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
			Statement createStatement = connection.createStatement();
			ResultSet executeQuery = createStatement.executeQuery("select * from " + tableName);
			ResultSetMetaData metaData = executeQuery.getMetaData();
			int k = columnNames.size() * 2;
			//Object[] object = new Object[k];
			//System.out.println("Initial Lenght " + object.length);
			int i = 0;
			List<Object> objectList = new ArrayList<Object>();
			while (i < metaData.getColumnCount()) {
				i++;
				System.out.print(metaData.getColumnName(i) + "\t");
				columnNames.add(metaData.getColumnName(i));
				//System.out.println("==============" + object.length);
				objectList.add(metaData.getColumnName(i));
			}
			
			while (executeQuery.next()) {
				for (i = 0; i < columnNames.size(); i++) {
					System.out.print(executeQuery.getString("values are -----" +columnNames.get(i)) );
					objectList.add(executeQuery.getString(columnNames.get(i)));
				}
			}
			
			StringBuilder template = new StringBuilder();
			template.append("INSERT INTO "+ tableName + " (");
			
			for (String columnName : columnNames) {
				
			}
			for(int j=0;j<columnNames.size(); j++)
			{
				if(j == columnNames.size() - 1)
				{
					template.append(columnNames.get(j));
				}
				else
				{
					template.append(columnNames.get(j) + ", ");
				}
				
			}
			template.append(") VALUES ( ");
			
		    //Files.write(Paths.get("your-output.sql"), statements);*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
