package com.nielsen.engineering.wse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang.exception.ExceptionUtils;

public class DBConnection {

	public static Connection connection;

	public static boolean ConnectDB() throws IOException {
		boolean isDBConnect = true;

		try {
			Class.forName(ReadInputFile.properties
					.getProperty("SQLServerDriver"));// "com.microsoft.sqlserver.jdbc.SQLServerDriver"
			connection = DriverManager.getConnection(
					ReadInputFile.properties.getProperty("URL")
							+ ReadInputFile.properties
									.getProperty("ServerName") + ";"
							+ "databaseName="
							+ ReadInputFile.properties.getProperty("DBName"),
					ReadInputFile.properties.getProperty("UserName"),
					ReadInputFile.properties.getProperty("Password"));

		} catch (ClassNotFoundException | SQLException e) {
			isDBConnect = false;
			// TODO Auto-generated catch block
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(e));
			// e.printStackTrace();
		}
		return isDBConnect;
	}
}
