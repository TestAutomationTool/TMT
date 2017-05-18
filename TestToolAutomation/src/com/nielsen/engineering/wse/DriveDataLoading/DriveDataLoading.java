/**
 * 
 */
package com.nielsen.engineering.wse.DriveDataLoading;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author bhadraiah.kovuri
 * 
 */

@SuppressWarnings("unused")
public class DriveDataLoading {
	public static boolean DataLoading(String filepath, String filename,
			Connection connection, String TableName) {
		boolean IsInserted;
		try {

			StagingDataLoader loader = new StagingDataLoader();
			IsInserted = loader.loadStagingData(filepath + "\\" + filename,
					TableName, false, connection);

		} catch (Exception e) {
			IsInserted = false;
			e.printStackTrace();
		}
		// try {
		// // String SQL = "BULK INSERT dbo." + TableName + " FROM " + filepath
		// // + "\\" + filename + " WITH (FIELDTERMINATOR = '\t' )";
		// String SQL = "BULK INSERT dbo." + TableName + " FROM ' " +
		// filepath
		// + "\\" + filename +
		// ".TXT' WITH (FIELDTERMINATOR ='\t',ROWTERMINATOR ='\r\n');";
		// Statement stmt = connection.createStatement();
		// ResultSet rs = stmt.executeQuery(SQL);
		// while (rs.next()) {
		//
		// }
		// // PreparedStatement ps = connection.prepareStatement(SQL);
		// // ps.addBatch();
		// //
		// // ps.executeBatch(); // insert remaining records
		// // ps.close();
		//
		// // Iterate through the data in the result set and display it.
		// // String command =
		// "C:\\Program Files\\Microsoft SQL Server\\100\\Tools\\Binn\\bcp";
		// // String[] cmdArr = { command, TableName, "in",
		// // filepath +"\\"+ filename + ".TXT", "-c", "-b", 1000 + "", "-t",
		// // "|", "-r", System.lineSeparator(), "-S" };
		// //
		// // System.out.println("cammand array is:" + cmdArr.toString());
		// // System.out.println("BCP EXECUTION STARTED:");
		// // Runtime runTime = Runtime.getRuntime();
		// // Process p = null;
		// //
		// // try {
		// // p = runTime.exec(cmdArr);
		// // } catch (IOException e) {
		// // e.printStackTrace();
		// // }
		//
		// } catch (Exception ex) {
		// IsInserted = false;
		// ex.printStackTrace();
		// }
		return IsInserted;
	}

	public static boolean InsertFileTable(String line, Connection connection,
			String headers, String[] HeaderDataType, String INSERT_STATEMENT) {
		String values[] = null;
		boolean IsInserted = false;
		PreparedStatement pst = null;
		int i = 0;
		String[] headerArray = headers.split(",");

		try {
			String insertColumns = "";
			StringBuffer columnValue = new StringBuffer();
			for (int col = 0; col < headerArray.length; col++) {
				insertColumns = insertColumns + headerArray[col] + ",";
				columnValue.append("?,");
			}
			pst = connection.prepareStatement(INSERT_STATEMENT
					+ insertColumns.substring(0, insertColumns.length() - 1)
					+ ") values ("
					+ columnValue.subSequence(0, columnValue.length() - 1)
					+ ")");
			values = line.split("\t");
			for (i = 0; i <= values.length - 1; i++) {
				if (values[i] != null) {
					if (HeaderDataType[i].equalsIgnoreCase("String")) {
						pst.setString(i + 1, values[i]);
					} else if (HeaderDataType[i].equalsIgnoreCase("int")) {
						pst.setLong(i + 1, Long.parseLong(values[i]));
					} else {
						System.out.println("No data type matched");
					}
				}
			}
			pst.executeUpdate();
			IsInserted = true;
			// System.out.println("Successfully load data into database");

		} catch (Exception ex) {
			ex.printStackTrace();
			// System.out.println("load error:" + ex.getMessage());

		}
		return IsInserted;
	}
}
