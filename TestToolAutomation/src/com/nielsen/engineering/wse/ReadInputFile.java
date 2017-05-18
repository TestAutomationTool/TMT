/**
 * 
 */
package com.nielsen.engineering.wse;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.nielsen.engineering.wse.DriveDataLoading.LoadConfigurationData;
import com.nielsen.engineering.wse.ToStringClasses.TableNameAndClassMapping;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class ReadInputFile {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Properties properties = new Properties();

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, IOException {
		// TODO Auto-generated method stub
		ReadInputFileData(args[0]);
	}

	private static void ReadInputFileData(String configFilePath)
			throws IOException {
		properties.load(new FileInputStream(configFilePath));		
		/*
		 * Get the current timestamp from commonvariables class to create
		 * procecsslog file
		 */
		CommonVariables.getCurrentTimeStamp();
		/*
		 * Logger method initialize
		 */

		if (CommonVariables.LoggerInitialize()) {
			ProcesslogGenerator.Logger(properties.getProperty("EqualsOp"));
			ProcesslogGenerator.Logger("Build Version : "+ properties.getProperty("BuildVersion"));

			ProcesslogGenerator.Logger("Machine Name : " + CommonVariables.GetHostName());

			ProcesslogGenerator.Logger("Start Time: " + CommonVariables.getCurrentTimeStampWithDelims());
			ProcesslogGenerator.Logger("Start: DB Connection");

			/*
			 * Connect Database with Username,Password and Url
			 */
			boolean isDBconnected = DBConnection.ConnectDB();

			if (isDBconnected) {
				StagewiseValidation.StagewiseValidator(DBConnection.connection);
				ProcesslogGenerator.Logger("End: DB Connection Successfully");

				System.out.println("Data base connection successful");

				ProcesslogGenerator.Logger("Start: Deploy Table DDL Scripts");
				// Run the Table scripts
				if (CommonVariables.RunTableScript(DBConnection.connection)) {
					System.out.println("Table Script run Successfully");
					ProcesslogGenerator.Logger("End: Deploy Table DDL Scripts");
					// Truncate the all tables
					ProcesslogGenerator.Logger("Start: Truncate tables");
					if (CommonVariables.TruncateTables(DBConnection.connection)) {
						System.out.println("Truncate tables successfully");
						ProcesslogGenerator
								.Logger("End: Truncate tables successfully");
						/*
						 * Extracting Staging files
						 */
						ProcesslogGenerator
								.Logger("Start: Extracting the staging files");
						if (CommonVariables
								.ExtractStagingFiles(DBConnection.connection)) {
							ProcesslogGenerator
									.Logger("End: Extracting the staging files successfully");
							System.out
									.println("Successfully extraxted the staging files data");
							/*
							 * Stop the process for 5 seconds to avoid
							 * extraction deadlocks
							 */
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							/*
							 * Load Data into DB
							 */
							System.out
									.println("Start : inserting the staging files data into DB successfully");
							ProcesslogGenerator
									.Logger("Start: inserting the staging files data into DB");
							if (CommonVariables.LoadStagingFiles()) {
								ProcesslogGenerator
										.Logger("End: inserting the staging files data into DB successfully");
								System.out
										.println("End : inserting the staging files data into DB successfully");

								/*
								 * Load Config data into DB
								 */
								ProcesslogGenerator
										.Logger("Start: Loading the look up files data into DB");
								if (CommonVariables
										.CallLookUpDataMethods(DBConnection.connection)) {
									ProcesslogGenerator
											.Logger("End: Loading the look up files data into DB");
									System.out
											.println("Successfully inserted Configuration data into DB");

									/*
									 * Read TableNames and Mapping Class from
									 * xml
									 */
									List<TableNameAndClassMapping> tableNameAndClassMappingList = new ArrayList<TableNameAndClassMapping>();
									tableNameAndClassMappingList = LoadConfigurationData
											.TableNameAndClassMapping(properties
													.getProperty("TableAndClassMappingXml"));

									/*
									 * Call Class dynamically to excute method
									 * Input arguments are Which table to get
									 * the information ,which table to Inserted
									 * and DB Connection
									 */
									ProcesslogGenerator
											.Logger("Start: Generating intermediate table data by Reflection");
									for (TableNameAndClassMapping Mapping : tableNameAndClassMappingList) {
										try {
											Class<?> cls = Class
													.forName(Mapping
															.getClassName()
															.trim());
											Object obj = cls.newInstance();
											Method method = cls
													.getDeclaredMethod(
															cls.getMethods()[0]
																	.getName(),
															new Class[] {
																	Connection.class,
																	String.class,
																	String.class });
											method.invoke(
													obj,
													DBConnection.connection,
													Mapping.getParentTableName()
															.trim(),
													Mapping.getInsertTableName()
															.trim());
										} catch (IllegalArgumentException e) {
											e.printStackTrace();
										} catch (Exception ill) {
											ill.printStackTrace();
										}
									}
									ProcesslogGenerator
											.Logger("End: Generating intermediate table data by Reflection");
									/*
									 * Stop the process for 5 seconds to avoid
									 * dynamic class execution deadlocks
									 */
									try {
										Thread.sleep(5000);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									/*
									 * Validate the test cases and report
									 * generation
									 */
									if (TestCaseValidater
											.TestCaseValidate(DBConnection.connection)) {
										System.out.println("Done the Process");
										ProcesslogGenerator
												.Logger("Done the process");
										ProcesslogGenerator
												.Logger("End Time:"
														+ CommonVariables
																.getCurrentTimeStampWithDelims());
										ProcesslogGenerator.Logger(properties
												.getProperty("EqualsOp"));
									}

									try {
										DBConnection.connection.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									ProcesslogGenerator
											.Logger("Exception occur while inserting the configuration data into DB"
													+ "\n"
													+ properties
															.getProperty("EqualsOp"));
									System.out
											.println("Exception occur while inserting the configuration data into DB");
									return;
								}
							} else {
								ProcesslogGenerator
										.Logger("Exception occur while inserting the staging files"
												+ "\n"
												+ properties
														.getProperty("EqualsOp"));
								System.out
										.println("Exception occur while inserting the staging files");
								return;
							}

						} else {
							ProcesslogGenerator
									.Logger("Exception occur while extracting the staging files"
											+ "\n"
											+ properties
													.getProperty("EqualsOp"));
							System.out
									.println("Exception occur while extracting the staging files");
							return;
						}
					} else {
						ProcesslogGenerator
								.Logger("Exception occur while Truncating tables"
										+ "\n"
										+ properties.getProperty("EqualsOp"));
						System.out
								.println("Exception occur while Truncating tables");
						return;
					}
				} else {
					ProcesslogGenerator
							.Logger("Exception occur while Table Script run"
									+ "\n" + properties.getProperty("EqualsOp"));
					System.out
							.println("Exception occur while Table Script run");
					return;
				}

			} else {
				ProcesslogGenerator.Logger("Data base connection failed" + "\n"
						+ properties.getProperty("EqualsOp"));
				System.out.println("Data base connection failed");
				return;
			}
		} else {
			System.out.println("Exception ocuuer while initializing the Logger"
					+ "\n" + properties.getProperty("EqualsOp"));
			return;
		}
	}
}