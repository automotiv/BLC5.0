package com.sptools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

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
			Object[] object = new Object[columnNames.size() * 2];
			int i = 0;
			
			while (i < metaData.getColumnCount()) {
				i++;
				System.out.print(metaData.getColumnName(i) + "\t");
				columnNames.add(metaData.getColumnName(i));
				System.out.println("==============" + object.length);
				object[i - 1] = metaData.getColumnName(i);
			}
			
			while (executeQuery.next()) {
				for (i = 0; i < columnNames.size(); i++) {
					System.out.print(executeQuery.getString(columnNames.get(i)) + "\t");
					object[i + columnNames.size()] = executeQuery.getString(columnNames.get(i));
				}
			}
			
			StringBuilder template = new StringBuilder();
			template.append("INSERT INTO"+ tableName + " (");
			for(int j=0;j<columnNames.size(); j++)
			{
				if(j == columnNames.size() - 1)
				{
					template.append("%s");
				}
				else
				{
					template.append("%s,");
				}
				
			}
			template.append(") VALUES ( ");
			for(int j=0;j<columnNames.size(); j++)
			{
				if(j == columnNames.size() - 1)
				{
					template.append("'%s'");
				}
				else
				{
					template.append("'%s',");
				}
				
			}
			template.append(");");
			System.out.println(template.toString());
			String string = new String();
			try {
				Method m = string.getClass().getMethod("format", new Class[] {});
				m.invoke(string, object);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			

			//final String template = "INSERT INTO TABLE(%s,%s,%s) VALUES ('%s','%s',%s);";

		    
		    /*for (int i = 1; i < lines.size(); i++) {
		        String[] values = lines.get(i).split(delimiter);
		        System.out.println((String.format(template, columnNames[0], columnNames[1], columnNames[2], values[0], values[1], values[2]));
		    }

		    Files.write(Paths.get("your-output.sql"), statements);*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
