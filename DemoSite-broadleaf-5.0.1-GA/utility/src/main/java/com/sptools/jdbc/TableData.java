package com.sptools.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class TableData {

	int totalNuberofTables = 0;
	
	public void getTableData(Connection connection, String tableName) {
		try {
			totalNuberofTables++;
			System.out.println("Table Data " + tableName);
			Vector<String> columnNames = new Vector<String>();
			Statement createStatement = connection.createStatement();
			ResultSet executeQuery = createStatement.executeQuery("select * from " + tableName);
			ResultSetMetaData metaData = executeQuery.getMetaData();
			int i = 0;
			System.out.println(executeQuery.getFetchSize());
			
			if(executeQuery.getFetchSize() > 0)
			{
				StringBuilder template = new StringBuilder();
				template.append("INSERT INTO " + "sptools_web." + tableName + " (");
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
			}
			System.out.println("totalNuberofTables --> " + totalNuberofTables);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
