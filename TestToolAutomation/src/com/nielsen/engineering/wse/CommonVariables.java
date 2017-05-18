package com.nielsen.engineering.wse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.nielsen.engineering.wse.DriveDataLoading.ConfigurationDataDBLoading;
//import com.nielsen.engineering.wse.DriveDataLoading.DriveDataLoading;
import com.nielsen.engineering.wse.DriveDataLoading.LoadConfigurationData;
import com.nielsen.engineering.wse.ToStringClasses.DriveIdInformation;
import com.nielsen.engineering.wse.ToStringClasses.LU_Aggrigation_Types;
import com.nielsen.engineering.wse.ToStringClasses.LU_Collection_Type;
import com.nielsen.engineering.wse.ToStringClasses.LU_DateTime_Fields;
import com.nielsen.engineering.wse.ToStringClasses.LU_Entity_Type;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Definitions;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Info;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Phase;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Phase_Correlations;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_State;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Tables;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Transition_Definitions;
import com.nielsen.engineering.wse.ToStringClasses.LU_MetricValidRanges;
import com.nielsen.engineering.wse.ToStringClasses.LU_Metric_Definitions;
import com.nielsen.engineering.wse.ToStringClasses.LU_Metric_Names;
import com.nielsen.engineering.wse.ToStringClasses.LU_Result_Information;
import com.nielsen.engineering.wse.ToStringClasses.LU_Task_Results;
import com.nielsen.engineering.wse.ToStringClasses.LU_Task_Types;
import com.nielsen.engineering.wse.ToStringClasses.LU_Technology_Mode;
import com.nielsen.engineering.wse.ToStringClasses.LU_TextFile_Collection_Type;
import com.nielsen.engineering.wse.ToStringClasses.LU_Textfile_Name;
import com.nielsen.engineering.wse.ToStringClasses.MD_TestCase_Status;
import com.nielsen.engineering.wse.ToStringClasses.MarketCarrierInformation;
//import com.nielsen.engineering.wse.ToStringClasses.StagingTablesList;
import com.nielsen.engineering.wse.ToStringClasses.TestCases;

public class CommonVariables {

	public static String FileHeader = null;
	public static String[] FileHeaderDataType = null;
	public static String INSERT_STATEMENT = "INSERT INTO [Table_Name] (";
	public static List<String> lines = null;
	public String COMMA = ",";
	public static String currenttimestamp;
	// public String sQuery =
	// "SELECT COUNT(*) RecCount FROM [Table_Name] WHERE [Column_Name] [Condition] ";
	public String sQuery = "SELECT COUNT(*) RecCount FROM METRIC_SAMPLE_COUNT WHERE METRIC_ID = [Metric_Id] AND METRIC_SAMPLE_COUNT [Condition]";

	/*
	 * Call configuration data methods to insert look up tables data into DB
	 */

	public static boolean CallLookUpDataMethods(Connection connection) {
		boolean IsConfigDataInserted = true;

		try {
			/*
			 * Load DriveId Information into DB
			 */

			List<DriveIdInformation> DriveIDinfo = new ArrayList<DriveIdInformation>();
			DriveIDinfo = LoadConfigurationData
					.DriveIDInfo(ReadInputFile.properties
							.getProperty("DriveIDInformationXml"));

			boolean IsDriveIDinfoDataLoad = ConfigurationDataDBLoading
					.LoadDriveIdInfo(connection, DriveIDinfo);
			if (IsDriveIDinfoDataLoad) {
				System.out.println("Successfully Inserted DriveID Info");
			}

			/*
			 * Load MarketCarrier Information into DB
			 */

			List<MarketCarrierInformation> MarketCarrierInfo = new ArrayList<MarketCarrierInformation>();
			MarketCarrierInfo = LoadConfigurationData
					.MarketCarrierInfo(ReadInputFile.properties
							.getProperty("MarketCarrierInfoXml"));

			boolean IsMarketCarrierDataLoad = ConfigurationDataDBLoading
					.LoadMarketCarrierInfo(connection, MarketCarrierInfo);
			if (IsMarketCarrierDataLoad) {
				System.out.println("Successfully Inserted Market Carrier Info");
			}

			/*
			 * Load Result Information into DB
			 */

			List<LU_Result_Information> ResultInfo = new ArrayList<LU_Result_Information>();
			ResultInfo = LoadConfigurationData
					.ResultInformation(ReadInputFile.properties
							.getProperty("ResultInformationXml"));

			boolean IsResultInfoDataLoad = ConfigurationDataDBLoading
					.LoadResultInfo(connection, ResultInfo);
			if (IsResultInfoDataLoad) {
				System.out.println("Successfully Inserted result Information");
			}

			/*
			 * Load EventDefinitions Information into DB
			 */

			List<LU_Event_Definitions> EventDefInfo = new ArrayList<LU_Event_Definitions>();
			EventDefInfo = LoadConfigurationData
					.EventDefinitions(ReadInputFile.properties
							.getProperty("EventDefinitionXml"));

			boolean IsEventDefInfoDataLoad = ConfigurationDataDBLoading
					.LoadEventDefinitions(connection, EventDefInfo);
			if (IsEventDefInfoDataLoad) {
				System.out
						.println("Successfully Inserted Event definition Information");
			}

			/*
			 * Load EventTransitionDefinitions Information into DB
			 */

			List<LU_Event_Transition_Definitions> EventTransDefInfo = new ArrayList<LU_Event_Transition_Definitions>();
			EventTransDefInfo = LoadConfigurationData
					.EventTransitionDefinitions(ReadInputFile.properties
							.getProperty("EventTransitionDefinitionXml"));

			boolean IsEventTransDefInfoDataLoad = ConfigurationDataDBLoading
					.LoadEventTransitionDefinitions(connection,
							EventTransDefInfo);
			if (IsEventTransDefInfoDataLoad) {
				System.out
						.println("Successfully Inserted event Tarsition Information");
			}

			/*
			 * Load MetricDefinitions DB
			 */

			List<LU_Metric_Definitions> MetricDefinitions = new ArrayList<LU_Metric_Definitions>();
			MetricDefinitions = LoadConfigurationData
					.MetricDefinitions(ReadInputFile.properties
							.getProperty("MetricDefinitionsXml"));

			boolean IsMetricDefDataLoad = ConfigurationDataDBLoading
					.LoadMetricDefinitions(connection, MetricDefinitions);
			if (IsMetricDefDataLoad) {
				System.out
						.println("Successfully Inserted Metric Definitions Info");
			}

			/*
			 * Load MetricValidRanges DB
			 */

			List<LU_MetricValidRanges> MetricValidRanges = new ArrayList<LU_MetricValidRanges>();
			MetricValidRanges = LoadConfigurationData
					.MetricValiedRanges(ReadInputFile.properties
							.getProperty("MetricValidRanges"));

			boolean IsMetricValidDataLoad = ConfigurationDataDBLoading
					.LoadMetricValidRanges(connection, MetricValidRanges);
			if (IsMetricValidDataLoad) {
				System.out
						.println("Successfully Inserted Metric Valid Ranges  Info");
			}

			/*
			 * Load Aggigation Types DB
			 */

			List<LU_Aggrigation_Types> AggigationTypes = new ArrayList<LU_Aggrigation_Types>();
			AggigationTypes = LoadConfigurationData
					.AggrigationTypes(ReadInputFile.properties
							.getProperty("AggrigationType"));

			boolean IsAggigationTypesDataLoad = ConfigurationDataDBLoading
					.LoadAggrigationTypes(connection, AggigationTypes);
			if (IsAggigationTypesDataLoad) {
				System.out
						.println("Successfully Inserted Aggigation Types Info");
			}

			/*
			 * Load Entity Types DB
			 */

			List<LU_Entity_Type> EntityTypes = new ArrayList<LU_Entity_Type>();
			EntityTypes = LoadConfigurationData
					.EntityTypes(ReadInputFile.properties
							.getProperty("EntityType"));

			boolean IsEntityTypesDataLoad = ConfigurationDataDBLoading
					.LoadEntityTypes(connection, EntityTypes);
			if (IsEntityTypesDataLoad) {
				System.out.println("Successfully Inserted Entity Types Info");
			}

			/*
			 * Load Event Definitions DB
			 */

			List<LU_Event_Definitions> EventDefinitions = new ArrayList<LU_Event_Definitions>();
			EventDefinitions = LoadConfigurationData
					.EventDefinitions(ReadInputFile.properties
							.getProperty("MetricDefinitions"));

			boolean IsEventDefinitionsDataLoad = ConfigurationDataDBLoading
					.LoadEventDefinitions(connection, EventDefinitions);
			if (IsEventDefinitionsDataLoad) {
				System.out
						.println("Successfully Inserted Event Definitions Info");
			}

			/*
			 * Load EventPhase DB
			 */

			List<LU_Event_Phase> EventPhase = new ArrayList<LU_Event_Phase>();
			EventPhase = LoadConfigurationData
					.EventPhase(ReadInputFile.properties
							.getProperty("EventPhase"));

			boolean IsEventPhaseDataLoad = ConfigurationDataDBLoading
					.LoadEventPhases(connection, EventPhase);
			if (IsEventPhaseDataLoad) {
				System.out.println("Successfully Inserted EventPhase Info");
			}

			/*
			 * Load EventState DB
			 */

			List<LU_Event_State> EventState = new ArrayList<LU_Event_State>();
			EventState = LoadConfigurationData
					.EventSates(ReadInputFile.properties
							.getProperty("EventState"));

			boolean IsEventStateDataLoad = ConfigurationDataDBLoading
					.LoadEventStates(connection, EventState);
			if (IsEventStateDataLoad) {
				System.out.println("Successfully Inserted EventState Info");
			}

			/*
			 * Load Metric Definitions DB
			 */

			List<LU_Metric_Definitions> lMetricDefinitions = new ArrayList<LU_Metric_Definitions>();
			lMetricDefinitions = LoadConfigurationData
					.MetricDefinitions(ReadInputFile.properties
							.getProperty("MetricDefinitions"));

			boolean IsMetricDefinitionsDataLoad = ConfigurationDataDBLoading
					.LoadMetricDefinitions(connection, lMetricDefinitions);
			if (IsMetricDefinitionsDataLoad) {
				System.out
						.println("Successfully Inserted Metric Definitions Info");
			}

			/*
			 * Load Metric Names DB
			 */

			List<LU_Metric_Names> MetricNames = new ArrayList<LU_Metric_Names>();
			MetricNames = LoadConfigurationData
					.MetricNames(ReadInputFile.properties
							.getProperty("MetricNames"));

			boolean IsMetriNamesDataLoad = ConfigurationDataDBLoading
					.LoadMetricNames(connection, MetricNames);
			if (IsMetriNamesDataLoad) {
				System.out.println("Successfully Inserted Metric Names Info");
			}

			/*
			 * Load TaskResults Names DB
			 */

			List<LU_Task_Results> TaskResults = new ArrayList<LU_Task_Results>();
			TaskResults = LoadConfigurationData
					.TaskResults(ReadInputFile.properties
							.getProperty("TaskResults"));

			boolean IsTaskResultsDataLoad = ConfigurationDataDBLoading
					.LoadTaskResults(connection, TaskResults);
			if (IsTaskResultsDataLoad) {
				System.out.println("Successfully Inserted Task Results Info");
			}

			/*
			 * Load Task Types Names DB
			 */

			List<LU_Task_Types> TaskTypes = new ArrayList<LU_Task_Types>();
			TaskTypes = LoadConfigurationData
					.TaskTypes(ReadInputFile.properties.getProperty("TaskType"));

			boolean IsTaskTypeDataLoad = ConfigurationDataDBLoading
					.LoadTaskTypes(connection, TaskTypes);
			if (IsTaskTypeDataLoad) {
				System.out.println("Successfully Inserted Task Types Info");
			}

			/*
			 * Load Technology Modes DB
			 */

			List<LU_Technology_Mode> TechnologyModes = new ArrayList<LU_Technology_Mode>();
			TechnologyModes = LoadConfigurationData
					.TechnologyMode(ReadInputFile.properties
							.getProperty("TechnologyModes"));

			boolean IsTechnologyModesDataLoad = ConfigurationDataDBLoading
					.LoadTechnologyModes(connection, TechnologyModes);
			if (IsTechnologyModesDataLoad) {
				System.out
						.println("Successfully Inserted TechnologyModes Info");
			}

			/*
			 * Load Text Files DB
			 */

			List<LU_Textfile_Name> TextFiles = new ArrayList<LU_Textfile_Name>();
			TextFiles = LoadConfigurationData
					.TextFiles(ReadInputFile.properties
							.getProperty("TextFileNames"));

			boolean IsTextFilesDataLoad = ConfigurationDataDBLoading
					.LoadTextFileNames(connection, TextFiles);
			if (IsTextFilesDataLoad) {
				System.out.println("Successfully Inserted Text Files Info");
			}

			/*
			 * Load Test Cases DB
			 */

			List<TestCases> TestCases = new ArrayList<TestCases>();
			TestCases = LoadConfigurationData
					.TestCases(ReadInputFile.properties
							.getProperty("TestCases"));

			boolean IsTestCaseDataLoad = ConfigurationDataDBLoading
					.LoadTestCases(connection, TestCases);
			if (IsTestCaseDataLoad) {
				System.out.println("Successfully Inserted Test Cases Info");
			}

			/*
			 * Load Collection type Information into DB
			 */

			List<LU_Collection_Type> Collectiontypeinfo = new ArrayList<LU_Collection_Type>();
			Collectiontypeinfo = LoadConfigurationData
					.CollectionTypes(ReadInputFile.properties
							.getProperty("CollectionTypesXml"));

			boolean IsCollectionTypeinfoDataLoad = ConfigurationDataDBLoading
					.LoadCollectionTypes(connection, Collectiontypeinfo);
			if (IsCollectionTypeinfoDataLoad) {
				System.out
						.println("Successfully Inserted Collection Types Info");
			}

			/*
			 * Load Event Message Tables Information into DB
			 */

			List<LU_Event_Tables> EventMessageTablesinfo = new ArrayList<LU_Event_Tables>();
			EventMessageTablesinfo = LoadConfigurationData
					.EventMessageTables(ReadInputFile.properties
							.getProperty("EventMessageTablesXml"));

			boolean IsEventMessageTablesDataLoad = ConfigurationDataDBLoading
					.LoadEventMessageTables(connection, EventMessageTablesinfo);
			if (IsEventMessageTablesDataLoad) {
				System.out
						.println("Successfully Inserted Event Message Tables Info");
			}

			/*
			 * Load Event Field Tables Information into DB
			 */

			List<LU_Event_Tables> EventFieldTablesinfo = new ArrayList<LU_Event_Tables>();
			EventFieldTablesinfo = LoadConfigurationData
					.EventFieldTables(ReadInputFile.properties
							.getProperty("EventFieldTablesXml"));

			boolean IsEventFieldTablesDataLoad = ConfigurationDataDBLoading
					.LoadEventFieldTables(connection, EventFieldTablesinfo);
			if (IsEventFieldTablesDataLoad) {
				System.out
						.println("Successfully Inserted Event Field Tables Info");
			}

			/*
			 * Load Event Phase Correlations Information into DB
			 */

			List<LU_Event_Phase_Correlations> EventPhaseCorrelationsinfo = new ArrayList<LU_Event_Phase_Correlations>();
			EventPhaseCorrelationsinfo = LoadConfigurationData
					.PhaseEventCorrelations(ReadInputFile.properties
							.getProperty("EventPhaseCorrelationXml"));

			boolean IsPhaseEventDataLoad = ConfigurationDataDBLoading
					.LoadEventPhaseCorrelations(connection,
							EventPhaseCorrelationsinfo);
			if (IsPhaseEventDataLoad) {
				System.out
						.println("Successfully Inserted Phase event correlations Info");
			}

			/*
			 * Load Event Infos Information into DB
			 */

			List<LU_Event_Info> EventInfos = new ArrayList<LU_Event_Info>();
			EventInfos = LoadConfigurationData
					.EventInfo(ReadInputFile.properties
							.getProperty("EventInfoXml"));

			boolean IsEventInfoDataLoad = ConfigurationDataDBLoading
					.LoadEventInfos(connection, EventInfos);
			if (IsEventInfoDataLoad) {
				System.out
						.println("Successfully Inserted event Info information");
			}

			/*
			 * Date time fields Infos Information into DB
			 */

			List<LU_DateTime_Fields> datetimefieldsInfos = new ArrayList<LU_DateTime_Fields>();
			datetimefieldsInfos = LoadConfigurationData
					.DateTimeFields(ReadInputFile.properties
							.getProperty("DateTimeFieldsXml"));

			boolean IsDatetimefieldsDataLoad = ConfigurationDataDBLoading
					.LoadDatetimeFields(connection, datetimefieldsInfos);
			if (IsDatetimefieldsDataLoad) {
				System.out
						.println("Successfully Inserted Date time fileds Info information");
			}

			/*
			 * Textfile name and ollection Information into DB
			 */

			List<LU_TextFile_Collection_Type> TNFCInfos = new ArrayList<LU_TextFile_Collection_Type>();
			TNFCInfos = LoadConfigurationData
					.TextFileName_CollectionType(ReadInputFile.properties
							.getProperty("TFNCXml"));

			boolean IsFNCDataLoad = ConfigurationDataDBLoading.LoadTFNC(
					connection, TNFCInfos);
			if (IsFNCDataLoad) {
				System.out
						.println("Successfully Inserted TextFilename and collection type Info information");
			}

			/*
			 * Test Case Status Information into DB
			 */

			List<MD_TestCase_Status> MDTCSInfos = new ArrayList<MD_TestCase_Status>();
			MDTCSInfos = LoadConfigurationData
					.TestCaseStatus(ReadInputFile.properties
							.getProperty("TestCaseStatusXml"));

			boolean IsMDTCSDataLoad = ConfigurationDataDBLoading
					.LoadTestCaseStatus(connection, MDTCSInfos);
			if (IsMDTCSDataLoad) {
				System.out
						.println("Successfully Inserted Test Case status information");
			}

		} catch (Exception ex) {
			IsConfigDataInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();

		}
		return IsConfigDataInserted;
	}

	public static boolean ExtractStagingFiles(Connection connection) {
		boolean IsStagingFilesExtracted = true;

		try {

			/*
			 * Create Staging files extract Directory to store unzip files not
			 * create or else it create and Store the output file
			 */

			String dirname = ReadInputFile.properties
					.getProperty("ExtractLocation");
			Path p1 = Paths.get(dirname);
			@SuppressWarnings("unused")
			boolean isDiretoryExist;
			File dir = new File(dirname);
			if (!dir.exists()) {
				// System.out.println("creating directory: " + directoryName);
				isDiretoryExist = false;

				try {
					Files.createDirectories(p1);
					isDiretoryExist = true;
				} catch (SecurityException se) {
					se.printStackTrace();
				}
			}

			/*
			 * Create Output Directory to store Reports files not create or else
			 * it create and Store the output file
			 */

			String reportsdirname = ReadInputFile.properties
					.getProperty("ReportsDirctory");
			Path p2 = Paths.get(reportsdirname);
			@SuppressWarnings("unused")
			boolean isReportsDiretoryExist;
			File repdir = new File(reportsdirname);
			if (!repdir.exists()) {
				// System.out.println("creating directory: " + directoryName);
				isReportsDiretoryExist = false;

				try {
					Files.createDirectories(p2);
					isReportsDiretoryExist = true;
				} catch (SecurityException se) {
					se.printStackTrace();
				}
			}
			/*
			 * delete the existing files before extracting the Zip files
			 * Extraction Directory
			 */
			if (dir.exists()) {
				FileUtils.cleanDirectory(new File(dirname));
			} else {
				ProcesslogGenerator
						.Logger("The Staging files extract directory does not exist");
			}
			// Clean Reports directory
			// if (repdir.exists()) {
			// FileUtils.cleanDirectory(new File(reportsdirname));
			// } else {
			// ProcesslogGenerator
			// .Logger("The reprts generated directory does not exist");
			// }
			/*
			 * Read Staging Tables from xML
			 */
			// List<StagingTablesList> stagingTablesList = new
			// ArrayList<StagingTablesList>();
			// stagingTablesList = LoadConfigurationData
			// .ReadStagingTables(ReadInputFile.properties
			// .getProperty("StagingTablesListXml"));

			/*
			 * Extract the Zip files
			 */

			File folder = new File(
					ReadInputFile.properties.getProperty("ZipFiles"));
			File[] InputZipFiles = folder.listFiles();
			for (File file : InputZipFiles) {

				String FolderNameWithoutExtension = file.getName()
						.replaceFirst("[.][^.]+$", "");

				boolean IsExtracted = extractFolder(file.getAbsolutePath(),
						dirname + FolderNameWithoutExtension);
				if (IsExtracted) {
					System.out.println("Successfully extracted the zip file  "
							+ FolderNameWithoutExtension + "in  " + dirname);
					// File[] files = new File(
					// ReadInputFile.properties
					// .getProperty("ExtractLocation")
					// + "\\"
					// + FolderNameWithoutExtension).listFiles();
					// for (File sfile : files) {
					// if (!sfile.isFile())
					// continue;
					// for (StagingTablesList strTableName : stagingTablesList)
					// {
					// if (sfile
					// .getName()
					// .replaceFirst("[.][^.]+$", "")
					// .equalsIgnoreCase(
					// strTableName.getTableName()
					// .toString())) {
					// boolean IsInserted = DriveDataLoading
					// .DataLoading(
					// ReadInputFile.properties
					// .getProperty("ExtractLocation")
					// + "\\"
					// + FolderNameWithoutExtension,
					// sfile.getName(),
					// connection,
					// "dbo."
					// + sfile.getName()
					// .replaceFirst(
					// "[.][^.]+$",
					// ""));
					//
					// if (IsInserted) {
					// System.out
					// .println("Data Inseted successfully for the Table in "
					// + strTableName);
					// }
					// }
					// }
					// }
				} else {
					System.out.println("Exception while extracting the file "
							+ FolderNameWithoutExtension);
				}
			}
		} catch (Exception ex) {
			IsStagingFilesExtracted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		return IsStagingFilesExtracted;
	}

	public static boolean extractFolder(String zipFile, String extractFolder) {
		boolean IsExtracted = true;
		try {
			int BUFFER = 2048;
			File file = new File(zipFile);
			@SuppressWarnings("resource")
			ZipFile zip = new ZipFile(file);
			String newPath = extractFolder;

			new File(newPath).mkdir();
			Enumeration<?> zipFileEntries = zip.entries();

			// Process each entry
			while (zipFileEntries.hasMoreElements()) {
				// grab a zip file entry
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				File destFile = new File(extractFolder, currentEntry);
				destFile = new File(newPath, destFile.getName());
				if (!entry.isDirectory()) {
					BufferedInputStream is = new BufferedInputStream(
							zip.getInputStream(entry));
					int currentByte;
					// establish buffer for writing file
					byte data[] = new byte[BUFFER];

					// write the current file to disk
					FileOutputStream fos = new FileOutputStream(destFile);
					BufferedOutputStream dest = new BufferedOutputStream(fos,
							BUFFER);

					// read and write until last byte is encountered
					while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, currentByte);
					}
					dest.flush();
					dest.close();
					is.close();
				}

			}
		} catch (Exception e) {
			IsExtracted = false;
			// Log("ERROR: "+e.getMessage());
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(e));
		}
		return IsExtracted;

	}

	@SuppressWarnings("unused")
	public static boolean LoadStagingFiles() {

		boolean IsStagingDataLoad = true;
		final String dosCommand = "cmd /c for /f \"tokens=*\" %l in ("
				+ ReadInputFile.properties.getProperty("StagingFileNames")
				+ ") do for /f \"tokens=*\" %a in (\'dir /s /b  a-d \""
				+ ReadInputFile.properties.getProperty("ExtractLocation")
				+ "\"%l\"*\"\') do FOR /F \"tokens=5 delims=\\\" %c in (\"%a\")  do FOR /F \"tokens=1 delims=.\" %d in (\"%c\")  do SQLCMD -S "
				+ ReadInputFile.properties.getProperty("ServerName")
				+ " -d "
				+ ReadInputFile.properties.getProperty("DBName")
				+ " -U "
				+ ReadInputFile.properties.getProperty("UserName")
				+ " -P "
				+ ReadInputFile.properties.getProperty("Password")
				+ " -Q \"Bulk insert %d from \'%a\' WITH ( FIELDTERMINATOR = \'\\t\',ROWTERMINATOR = \'\\n\',ROWS_PER_BATCH = 10000,TABLOCK) \" /s";
		final String location = "C:\\WINDOWS\\system32";
		try {
			final Process process = Runtime.getRuntime().exec(
					dosCommand + " " + location);
			final InputStream in = process.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				// System.out.print((char) ch);
			}
		} catch (IOException e) {
			IsStagingDataLoad = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(e));
			// e.printStackTrace();
		}
		return IsStagingDataLoad;
	}

	/*
	 * Run the Table DDL Script
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static boolean RunTableScript(Connection conn) {
		boolean isDbScriptDeployed = true;
		try {
			/*
			 * Run Table Script
			 */
			List<String> tablescript = FileUtils.readLines(new File(ReadInputFile.properties.getProperty("TableDDLScript")));
			StringBuffer sb = new StringBuffer();
			for (String line : tablescript) {
				sb.append(line + "\n");
			}
			Statement st3 = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int Truncate = st3.executeUpdate(sb.toString());

		} catch (Exception ex) {
			isDbScriptDeployed = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		return isDbScriptDeployed;
	}

	/*
	 * truncate all tables in the specified DB
	 */
	@SuppressWarnings("unused")
	public static boolean TruncateTables(Connection conn) {
		boolean isTruncateTable = true;
		try {
			/*
			 * Run Table Script
			 */
			String sQuery = "USE "
					+ ReadInputFile.properties.getProperty("DBName")
					+ " EXEC sp_MSforeachtable 'TRUNCATE TABLE ?'";
			Statement st3 = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int Truncate = st3.executeUpdate(sQuery);

		} catch (Exception ex) {
			isTruncateTable = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		return isTruncateTable;
	}

	/*
	 * Current Datetimestamp
	 */
	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		currenttimestamp = sdf.format(now);
		return currenttimestamp;
	}

	public static String getCurrentTimeStampWithDelims() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
	}

	public static boolean LoggerInitialize() {
		boolean isLoggerInitialize = true;

		try {
			String filename = ReadInputFile.properties
					.getProperty("Processlog")
					+ "_"
					+ CommonVariables.currenttimestamp
					+ ReadInputFile.properties.getProperty("LogfileType");

			ProcesslogGenerator.updateLog4jConfiguration(filename);
		} catch (Exception ex) {
			isLoggerInitialize = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		return isLoggerInitialize;
	}

	public static String GetHostName() {
		String hostname = "Unknown";

		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException ex) {
			ProcesslogGenerator.Logger("Hostname can not be resolved");
		}
		return hostname;
	}
}
