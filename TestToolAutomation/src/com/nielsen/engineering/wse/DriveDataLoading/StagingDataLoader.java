/**
 * 
 */
package com.nielsen.engineering.wse.DriveDataLoading;

import java.io.FileReader;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import au.com.bytecode.opencsv.*;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class StagingDataLoader {

	private static final String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";
	private static boolean IsInserted = true;

	private char seprator = '\t';

	/**
	 * Parse Staging file using OpenCSV library and load in given database
	 * table.
	 * 
	 * @param inputFile
	 *            Input Staging file
	 * @param tableName
	 *            Database table name to import data
	 * @param truncateBeforeLoad
	 *            Truncate the table before inserting new records.
	 * @throws Exception
	 */
	public boolean loadStagingData(String InputFile, String tableName,
			boolean truncateBeforeLoad, Connection Connection) throws Exception {
		CSVReader csvReader = null;
		/*
		 * Check the connection is null If null Raise Not a valid connection
		 */
		if (null == Connection) {
			throw new Exception("Not a valid connection.");
		}
		/*
		 * Read table columns names and send input to staging data loader
		 */
		String[] Headers = null;
		String[] HeaderMetadata = null;
		Statement st = Connection.createStatement();
		String sql = "select * from " + tableName;
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();
		int rowCount = metaData.getColumnCount();
		if (rowCount > 0) {
			Headers = new String[rowCount];
			HeaderMetadata = new String[rowCount + 1];
			for (int i = 0; i < rowCount; i++) {
				Headers[i] = metaData.getColumnName(i + 1) + "\t";
				HeaderMetadata[i + 1] = metaData.getColumnTypeName(i + 1);
				// System.out.println(metaData.getColumnName(i + 1) + " \t");
			}
		}
		try {

			csvReader = new CSVReader(new FileReader(InputFile), this.seprator);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error occured while executing file. "
					+ e.getMessage());
		}
		String[] headerRow = Headers;// csvReader.readNext();

		if (null == headerRow) {
			System.out.println("Header Row is Empty");
		}

		String questionmarks = StringUtils.repeat("?,", headerRow.length);
		questionmarks = (String) questionmarks.subSequence(0,
				questionmarks.length() - 1);

		String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
		query = query
				.replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
		query = query.replaceFirst(VALUES_REGEX, questionmarks);
		// System.out.println("Query: " + query);

		String[] nextLine;
		PreparedStatement ps = null;
		try {
			Connection.setAutoCommit(false);
			ps = Connection.prepareStatement(query);
			if (truncateBeforeLoad) {
				// delete data from table before loading csv
				Connection.createStatement()
						.execute("DELETE FROM " + tableName);
			}
			final int batchSize = 1000;
			int count = 0;
			Date date = null;
			while ((nextLine = csvReader.readNext()) != null) {

				if (null != nextLine) {
					int index = 1;
					for (String string : nextLine) {
						// date = DateUtil.convertToDate(string);
						if (null != date) {
							ps.setDate(index, new java.sql.Date(date.getTime()));
						} else {
							/**
							 * Get the metada data of the database table and
							 * check what is the type of column and set the
							 * datatype accordingly
							 */
							if (HeaderMetadata[index]
									.equalsIgnoreCase("numeric")) {
								if (!string.isEmpty() && string != null) {
									ps.setDouble(index++,
											Double.parseDouble(string));
								} else {
									ps.setNull(index++, java.sql.Types.INTEGER);
								}

							} else if (HeaderMetadata[index]
									.equalsIgnoreCase("int")
									|| HeaderMetadata[index]
											.equalsIgnoreCase("smallint")
									|| HeaderMetadata[index]
											.equalsIgnoreCase("bigint")) {
								if (!string.isEmpty() && string != null) {
									ps.setLong(index++, Long.parseLong(string));
								} else {
									ps.setNull(index++, java.sql.Types.INTEGER);
								}
							} else if (HeaderMetadata[index]
									.equalsIgnoreCase("varchar")
									|| HeaderMetadata[index]
											.equalsIgnoreCase("datetime")) {

								ps.setString(index++, string);
							} else if (HeaderMetadata[index]
									.equalsIgnoreCase("char")) {

								ps.setString(index++, string);
							}
						}
					}
					ps.addBatch();
				}
				if (++count % batchSize == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch(); // insert remaining records
			Connection.commit();
		} catch (BatchUpdateException bue) {
			IsInserted = false;
			System.out.println("executeBatch threw BatchUpdateException: "
					+ bue.getMessage());
		} catch (Exception se) {
			IsInserted = false;
			se.printStackTrace();
		} finally {
			if (null != ps)
				ps.close();
			if (null != Connection)
				// Connection.close();
				csvReader.close();
		}
		return IsInserted;
	}

	public char getSeprator() {
		return seprator;
	}

	public void setSeprator(char seprator) {
		this.seprator = seprator;
	}

}
