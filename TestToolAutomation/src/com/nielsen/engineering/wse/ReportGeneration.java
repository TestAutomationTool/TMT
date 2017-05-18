/**
 * 
 */
package com.nielsen.engineering.wse;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.nielsen.engineering.wse.DriveDataLoading.LoadConfigurationData;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class ReportGeneration {
	static CommonVariables comm = new CommonVariables();
	private static StringBuffer sbTestCaseReport = new StringBuffer();
	private static StringBuffer sbCallTaskLevelReport = new StringBuffer();
	private static StringBuffer sbCarrierLevelReport = new StringBuffer();

	@SuppressWarnings("unused")
	public static boolean RangeGeneration(Connection conn,
			String AggrigationType, String Testcaseid) {

		boolean isRangeCal = true;
		ProcesslogGenerator.Logger("Start : Range calculation");
		try {

			/*
			 * Total Call task level details insert into DB
			 */
			String truncateTempTable = "IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[RANGE_VALIDATION_TOTAL_CALLS]') AND type in (N'U'))"

					+ "\r\n"
					+ " DROP TABLE [dbo].[RANGE_VALIDATION_TOTAL_CALLS]";

			Statement st3 = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int Truncate = st3.executeUpdate(truncateTempTable);

			String strRangevalidation = " SELECT * INTO RANGE_VALIDATION_TOTAL_CALLS FROM(SELECT AA.*,BB.MIN_VALUE AS LU_MIN_VALUE,BB.MAX_VALUE AS LU_MAX_VALUE FROM(SELECT AA.TESTCASE_ID,AA.TESTCASE_NAME,AA.METRIC_CODE,AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,MAX(MIN_VALUE) AS METRIC_MIN_VALUE,MAX(MAX_VALUE) AS METRIC_MAX_VALUE FROM(SELECT AA.TESTCASE_ID,AA.TESTCASE_NAME,AA.METRIC_CODE,BB.DRIVE_ID,BB.SOURCE_FILE_NAME,BB.CALL_ID,BB.TASK_ID, CASE WHEN SUBSTRING(BB.METRIC_ID,1,2)='04' THEN METRIC_VALUE END  MIN_VALUE, CASE WHEN SUBSTRING(BB.METRIC_ID,1,2)='05' THEN METRIC_VALUE END  MAX_VALUE FROM TESTCASES AA LEFT JOIN METRIC_AGG_DATA BB ON SUBSTRING(AA.METRIC_CODE,3,LEN(AA.METRIC_CODE))=SUBSTRING(BB.METRIC_ID,3,LEN(BB.METRIC_ID)) AND  SUBSTRING(BB.METRIC_ID,1,2) IN ('04','05') WHERE SUBSTRING( AA.METRIC_CODE,1,2)='"
					+ AggrigationType
					+ "' AND AA.TESTCASE_ID = '"
					+ Testcaseid
					+ "')AA GROUP BY AA.TESTCASE_ID,AA.TESTCASE_NAME,AA.METRIC_CODE,AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID)AA LEFT JOIN LU_Metric_Valid_Ranges BB ON SUBSTRING(AA.METRIC_CODE,13,6)=BB.METRIC_ID)AA ";
			Statement stRangevalidation = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int rsstRangevalidationresult = stRangevalidation
					.executeUpdate(strRangevalidation);
			ProcesslogGenerator.Logger("End : Range calculation");

			ProcesslogGenerator
					.Logger("Start : Range Metrics data inserting into TOTAL_CALL_TASK_LEVEL_REPORT table");
			String strTotalCallRangeValidation = "SELECT TESTCASE_ID TESTCASE_ID,TESTCASE_NAME TESTCASE_NAME,METRIC_CODE METRIC_CODE,DRIVE_ID DRIVE_ID,SOURCE_FILE_NAME SOURCE_FILE_NAME,CALL_ID CALL_ID,TASK_ID TASK_ID,METRIC_CODE AS METRIC_ID,METRIC_MIN_VALUE AS METRIC_VALUE FROM RANGE_VALIDATION_TOTAL_CALLS AA";

			Statement stTotalCallRangeValidation = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsTotalCallRangeValidation = stTotalCallRangeValidation
					.executeQuery(strTotalCallRangeValidation);
			while (rsTotalCallRangeValidation.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo.TOTAL_CALL_TASK_LEVEL_REPORT (TESTCASE_ID, TESTCASE_NAME, METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE) values (?,?,?,?,?,?,?,?,?)");

				stmt.setString(1, (String) rsTotalCallRangeValidation
						.getObject("TESTCASE_ID"));
				stmt.setString(2, (String) rsTotalCallRangeValidation
						.getObject("TESTCASE_NAME"));

				if (!rsTotalCallRangeValidation.wasNull()) {
					stmt.setString(3, (String) rsTotalCallRangeValidation
							.getObject("METRIC_CODE"));
				} else {

					stmt.setNull(3, java.sql.Types.NVARCHAR);
				}
				if (!rsTotalCallRangeValidation.wasNull()
						&& rsTotalCallRangeValidation.getObject("DRIVE_ID") != null) {
					stmt.setInt(4, (int) rsTotalCallRangeValidation
							.getObject("DRIVE_ID"));
				} else {
					stmt.setNull(4, java.sql.Types.INTEGER);
				}
				if (!rsTotalCallRangeValidation.wasNull()) {
					stmt.setString(5, (String) rsTotalCallRangeValidation
							.getObject("SOURCE_FILE_NAME"));
				} else {
					stmt.setNull(5, java.sql.Types.NVARCHAR);
				}
				if (!rsTotalCallRangeValidation.wasNull()) {
					stmt.setInt(6, (int) rsTotalCallRangeValidation
							.getObject("CALL_ID"));
				} else {
					stmt.setNull(6, java.sql.Types.INTEGER);
				}
				if (!rsTotalCallRangeValidation.wasNull()) {
					stmt.setInt(7, (int) rsTotalCallRangeValidation.getInt(7));
				} else {
					stmt.setNull(7, java.sql.Types.INTEGER);
				}
				if (!rsTotalCallRangeValidation.wasNull()) {
					stmt.setString(8, (String) rsTotalCallRangeValidation
							.getObject("METRIC_CODE"));
				} else {
					stmt.setNull(8, java.sql.Types.NVARCHAR);
				}
				if (!rsTotalCallRangeValidation.wasNull()) {
					stmt.setInt(9, rsTotalCallRangeValidation.getInt(9));
				} else {
					stmt.setNull(9, java.sql.Types.INTEGER);
				}
				stmt.executeUpdate();
				conn.commit();
			}
			ProcesslogGenerator
					.Logger("End : Range Metrics data inserting into TOTAL_CALL_TASK_LEVEL_REPORT table");
			/*
			 * Failed Metric ranges insert into DB
			 */
			ProcesslogGenerator
					.Logger("Start : Range Metrics data inserting into FAILED_CALLL_TASK_LEVEL_REPORT table");
			String strFailedTestcases = "SELECT AA.TESTCASE_ID TESTCASE_ID,AA.TESTCASE_NAME TESTCASE_NAME,AA.METRIC_CODE METRIC_CODE,AA.DRIVE_ID DRIVE_ID,AA.SOURCE_FILE_NAME SOURCE_FILE_NAME,AA.CALL_ID CALL_ID,AA.TASK_ID TASK_ID,AA.METRIC_CODE AS METRIC_ID,METRIC_MIN_VALUE  AS COMMENTS,'NA' AS ROOTCAUSE,'NA' AS ISSUE_IN_MODULE,'NA' AS RECOMMENDED_ACTION FROM RANGE_VALIDATION_TOTAL_CALLS AA WHERE ((METRIC_MIN_VALUE <LU_MIN_VALUE OR METRIC_MIN_VALUE >LU_MAX_VALUE) OR(METRIC_MAX_VALUE <LU_MIN_VALUE OR METRIC_MAX_VALUE >LU_MAX_VALUE))";
			Statement stFailedTestcases = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsFailedTestcases = stFailedTestcases
					.executeQuery(strFailedTestcases);

			while (rsFailedTestcases.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo.FAILED_CALLL_TASK_LEVEL_REPORT(TESTCASE_ID, TESTCASE_NAME, METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE,ROOTCAUSE,ISSUE_IN_MODULE,RECOMMENDED_ACTION) values (?,?,?,?,?,?,?,?,?,?,?,?)");

				stmt.setString(1,
						(String) rsFailedTestcases.getObject("TESTCASE_ID"));
				stmt.setString(2,
						(String) rsFailedTestcases.getObject("TESTCASE_NAME"));
				stmt.setString(3,
						(String) rsFailedTestcases.getObject("METRIC_CODE"));
				stmt.setInt(4, (int) rsFailedTestcases.getObject("DRIVE_ID"));
				stmt.setString(5, (String) rsFailedTestcases
						.getObject("SOURCE_FILE_NAME"));
				stmt.setInt(6, (int) rsFailedTestcases.getObject("CALL_ID"));
				stmt.setInt(7, (int) rsFailedTestcases.getInt(7));
				stmt.setString(8,
						(String) rsFailedTestcases.getObject("METRIC_ID"));
				stmt.setInt(9, rsFailedTestcases.getInt(9));
				stmt.setString(10,
						(String) rsFailedTestcases.getObject("ROOTCAUSE"));
				stmt.setString(11,
						(String) rsFailedTestcases.getObject("ISSUE_IN_MODULE"));
				stmt.setString(12, (String) rsFailedTestcases
						.getObject("RECOMMENDED_ACTION"));
				stmt.executeUpdate();
				conn.commit();
			}
			ProcesslogGenerator
					.Logger("End : Range Metrics data inserting into FAILED_CALLL_TASK_LEVEL_REPORT table");
		} catch (Exception ex) {
			isRangeCal = false;
			ex.printStackTrace();
		}
		return isRangeCal;
	}

	public static boolean NullGeneration(Connection conn,
			String AggrigationType, String Testcaseid) {
		boolean isNullCal = true;
		ProcesslogGenerator.Logger("Start : Metric Null calculation");
		try {

			/*
			 * Null Check value insert into DB
			 */

			String strtotalcalltasklevel = "SELECT AA.TESTCASE_ID TESTCASE_ID,AA.TESTCASE_NAME TESTCASE_NAME,AA.METRIC_CODE METRIC_CODE,AA.DRIVE_ID DRIVE_ID,AA.SOURCE_FILE_NAME SOURCE_FILE_NAME,AA.CALL_ID CALL_ID,AA.TASK_ID TASK_ID,AA.METRIC_ID METRIC_ID,AA.METRIC_VALUE METRIC_VALUE FROM(select aa.*,BB.METRIC_ID,BB.METRIC_VALUE  from(SELECT distinct AA.TESTCASE_ID,AA.TESTCASE_NAME,AA.METRIC_CODE,AA.AGGREATION_TYPE_ID,BB.DRIVE_ID,BB.SOURCE_FILE_NAME,BB.CALL_ID,BB.TASK_ID FROM( SELECT AA.*,BB.COLLECTION_TYPE FROM TESTCASES AA INNER JOIN(SELECT AA.*,TEXTFILE_ID FROM LU_TEXTFILE_COLLECTION_TYPE AA INNER JOIN LU_TEXTFILE_NAME BB ON AA.TEXTFILE_NAME=BB.TEXTFILE_NAME)BB ON AA.TEXTFILE_ID=BB.TEXTFILE_ID)AA INNER JOIN (SELECT AA.*,BB.COLLECTION_TYPE FROM AUT_MODE_INFO AA INNER JOIN AUT_DRIVE_INFO BB ON AA.DRIVE_ID=BB.DRIVE_ID)BB ON AA.EVENT_PHASE_ID=BB.PHASE_ID and ( AA.TECHNOLOGY_MODE_ID=BB.TECHNOLOGY_MODE_ID OR AA.TECHNOLOGY_MODE_ID=BB.DEFAULT_MODE_ID) AND AA.COLLECTION_TYPE=BB.COLLECTION_TYPE AND AA.AGGREATION_TYPE_ID ='"
					+ AggrigationType
					+ "' AND AA.TESTCASE_ID='"
					+ Testcaseid
					+ "')aa LEFT JOIN METRIC_AGG_DATA BB ON aa.SOURCE_FILE_NAME=bb.SOURCE_FILE_NAME and aa.call_id=bb.CALL_ID and ISNULL(aa.task_id,0)=ISNULL(bb.task_id,0) and AA.METRIC_CODE=BB.METRIC_ID WHERE AA.AGGREATION_TYPE_ID = '"
					+ AggrigationType
					+ "' AND AA.TESTCASE_ID='"
					+ Testcaseid
					+ "')AA";
			Statement sttotalcalltasklevel = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rstotalcalltasklevel = sttotalcalltasklevel
					.executeQuery(strtotalcalltasklevel);
			ProcesslogGenerator.Logger("End : Metric Null calculation");

			ProcesslogGenerator
					.Logger("Start : Null Metrics data inserting into TOTAL_CALL_TASK_LEVEL_REPORT table");
			while (rstotalcalltasklevel.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo.TOTAL_CALL_TASK_LEVEL_REPORT (TESTCASE_ID, TESTCASE_NAME, METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE) values (?,?,?,?,?,?,?,?,?)");

				stmt.setString(1,
						(String) rstotalcalltasklevel.getObject("TESTCASE_ID"));
				stmt.setString(2, (String) rstotalcalltasklevel
						.getObject("TESTCASE_NAME"));

				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setString(3, (String) rstotalcalltasklevel
							.getObject("METRIC_CODE"));
				} else {

					stmt.setNull(3, java.sql.Types.NVARCHAR);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(4,
							(int) rstotalcalltasklevel.getObject("DRIVE_ID"));
				} else {
					stmt.setNull(4, java.sql.Types.INTEGER);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setString(5, (String) rstotalcalltasklevel
							.getObject("SOURCE_FILE_NAME"));
				} else {

					stmt.setNull(5, java.sql.Types.NVARCHAR);
				}

				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(6,
							(int) rstotalcalltasklevel.getObject("CALL_ID"));
				}

				else {
					stmt.setNull(6, java.sql.Types.INTEGER);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(7, (int) rstotalcalltasklevel.getInt(7));
				} else {
					stmt.setNull(7, java.sql.Types.INTEGER);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setString(8, (String) rstotalcalltasklevel
							.getObject("METRIC_CODE"));
				} else {
					stmt.setNull(8, java.sql.Types.NVARCHAR);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(9, rstotalcalltasklevel.getInt(9));
				} else {
					stmt.setNull(9, java.sql.Types.INTEGER);
				}
				stmt.executeUpdate();
				conn.commit();
			}
			ProcesslogGenerator
					.Logger("End : Null Metrics data inserting into TOTAL_CALL_TASK_LEVEL_REPORT table");

			ProcesslogGenerator
					.Logger("Start : Null Metrics data inserting into FAILED_CALLL_TASK_LEVEL_REPORT table");

			String strFailedTestcases = "SELECT TESTCASE_ID,TESTCASE_NAME,METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE,'NA' AS ROOTCAUSE,'NA' AS ISSUE_IN_MODULE,'NA' AS RECOMMENDED_ACTION FROM  TOTAL_CALL_TASK_LEVEL_REPORT WHERE METRIC_VALUE > 0  AND SUBSTRING(METRIC_CODE,1,2) = '"
					+ AggrigationType + "'AND TESTCASE_ID='" + Testcaseid + "'";

			Statement stFailedTestcases = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsFailedTestcases = stFailedTestcases
					.executeQuery(strFailedTestcases);

			while (rsFailedTestcases.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo.FAILED_CALLL_TASK_LEVEL_REPORT(TESTCASE_ID, TESTCASE_NAME, METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE,ROOTCAUSE,ISSUE_IN_MODULE,RECOMMENDED_ACTION) values (?,?,?,?,?,?,?,?,?,?,?,?)");

				stmt.setString(1,
						(String) rsFailedTestcases.getObject("TESTCASE_ID"));
				stmt.setString(2,
						(String) rsFailedTestcases.getObject("TESTCASE_NAME"));
				stmt.setString(3,
						(String) rsFailedTestcases.getObject("METRIC_CODE"));
				stmt.setInt(4, (int) rsFailedTestcases.getObject("DRIVE_ID"));
				stmt.setString(5, (String) rsFailedTestcases
						.getObject("SOURCE_FILE_NAME"));
				stmt.setInt(6, (int) rsFailedTestcases.getObject("CALL_ID"));
				stmt.setInt(7, (int) rsFailedTestcases.getInt(7));
				stmt.setString(8,
						(String) rsFailedTestcases.getObject("METRIC_CODE"));
				stmt.setInt(9, rsFailedTestcases.getInt(9));
				stmt.setString(10,
						(String) rsFailedTestcases.getObject("ROOTCAUSE"));
				stmt.setString(11,
						(String) rsFailedTestcases.getObject("ISSUE_IN_MODULE"));
				stmt.setString(12, (String) rsFailedTestcases
						.getObject("RECOMMENDED_ACTION"));
				stmt.executeUpdate();
				conn.commit();
			}
			ProcesslogGenerator
					.Logger("End : Null Metrics data inserting into FAILED_CALLL_TASK_LEVEL_REPORT table");
		} catch (Exception ex) {
			isNullCal = false;
			ex.printStackTrace();

		}
		return isNullCal;
	}

	public static boolean DataAvailabilityGeneration(Connection conn,
			String AggrigationType, String Testcaseid) {
		boolean isDataAvailableCal = true;
		ProcesslogGenerator.Logger("Start : Data availability calculation");
		try {
			/*
			 * Task/Call level details
			 */
			String strtotalcalltasklevel = "SELECT AA.TESTCASE_ID TESTCASE_ID,AA.TESTCASE_NAME TESTCASE_NAME,BB.METRIC_CODE METRIC_CODE,BB.DRIVE_ID DRIVE_ID,BB.SOURCE_FILE_NAME SOURCE_FILE_NAME,BB.CALL_ID CALL_ID,BB.TASK_ID TASK_ID,BB.METRIC_ID METRIC_ID,BB.METRIC_VALUE METRIC_VALUE FROM TESTCASES AA LEFT JOIN(select aa.*,BB.METRIC_ID,BB.METRIC_VALUE from( SELECT distinct AA.TESTCASE_ID,AA.TESTCASE_NAME,AA.METRIC_CODE,AA.AGGREATION_TYPE_ID,BB.DRIVE_ID,BB.SOURCE_FILE_NAME,BB.CALL_ID,BB.TASK_ID FROM(SELECT AA.*,BB.COLLECTION_TYPE FROM TESTCASES AA INNER JOIN (SELECT AA.*,TEXTFILE_ID FROM LU_TEXTFILE_COLLECTION_TYPE AA INNER JOIN LU_TEXTFILE_NAME BB ON AA.TEXTFILE_NAME=BB.TEXTFILE_NAME)BB ON AA.TEXTFILE_ID=BB.TEXTFILE_ID)AA inner JOIN (SELECT AA.*,BB.COLLECTION_TYPE FROM AUT_MODE_INFO AA INNER JOIN AUT_DRIVE_INFO BB ON AA.DRIVE_ID=BB.DRIVE_ID)BB ON AA.EVENT_PHASE_ID=BB.PHASE_ID AND ( AA.TECHNOLOGY_MODE_ID=BB.TECHNOLOGY_MODE_ID OR AA.TECHNOLOGY_MODE_ID=BB.DEFAULT_MODE_ID) AND AA.COLLECTION_TYPE=BB.COLLECTION_TYPE AND AA.TESTCASE_ID= '"
					+ Testcaseid
					+ "' AND AA.AGGREATION_TYPE_ID = '"
					+ AggrigationType
					+ "')AA LEFT JOIN METRIC_AGG_DATA BB ON aa.SOURCE_FILE_NAME=bb.SOURCE_FILE_NAME and aa.call_id=bb.CALL_ID and ISNULL(aa.task_id,0)=ISNULL(bb.task_id,0) and AA.METRIC_CODE=BB.METRIC_ID WHERE AA.AGGREATION_TYPE_ID = '"
					+ AggrigationType
					+ "' AND AA.TESTCASE_ID = '"
					+ Testcaseid
					+ "')BB ON AA.TESTCASE_ID=BB.TESTCASE_ID WHERE AA.AGGREATION_TYPE_ID = '"
					+ AggrigationType
					+ "' AND AA.TESTCASE_ID = '"
					+ Testcaseid
					+ "'";

			Statement sttotalcalltasklevel = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rstotalcalltasklevel = sttotalcalltasklevel
					.executeQuery(strtotalcalltasklevel);
			ProcesslogGenerator.Logger("End : Data availability calculation");

			ProcesslogGenerator
					.Logger("Start : Data availability Metrics data inserting into TOTAL_CALL_TASK_LEVEL_REPORT table");
			while (rstotalcalltasklevel.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo.TOTAL_CALL_TASK_LEVEL_REPORT (TESTCASE_ID, TESTCASE_NAME, METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE) values (?,?,?,?,?,?,?,?,?)");

				stmt.setString(1,
						(String) rstotalcalltasklevel.getObject("TESTCASE_ID"));
				stmt.setString(2, (String) rstotalcalltasklevel
						.getObject("TESTCASE_NAME"));

				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setString(3, (String) rstotalcalltasklevel
							.getObject("METRIC_CODE"));
				} else {

					stmt.setNull(3, java.sql.Types.NVARCHAR);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(4,
							(int) rstotalcalltasklevel.getObject("DRIVE_ID"));
				} else {
					stmt.setNull(4, java.sql.Types.INTEGER);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setString(5, (String) rstotalcalltasklevel
							.getObject("SOURCE_FILE_NAME"));
				} else {

					stmt.setNull(5, java.sql.Types.NVARCHAR);
				}

				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(6,
							(int) rstotalcalltasklevel.getObject("CALL_ID"));
				}

				else {
					stmt.setNull(6, java.sql.Types.INTEGER);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(7, (int) rstotalcalltasklevel.getInt(7));
				} else {
					stmt.setNull(7, java.sql.Types.INTEGER);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setString(8, (String) rstotalcalltasklevel
							.getObject("METRIC_CODE"));
				} else {
					stmt.setNull(8, java.sql.Types.NVARCHAR);
				}
				if (!rstotalcalltasklevel.wasNull()) {
					stmt.setInt(9, rstotalcalltasklevel.getInt(9));
				} else {
					stmt.setNull(9, java.sql.Types.INTEGER);
				}
				stmt.executeUpdate();
				conn.commit();
			}
			ProcesslogGenerator
					.Logger("End : Data availability Metrics data inserting into TOTAL_CALL_TASK_LEVEL_REPORT table");
			/*
			 * FAILED CALL/TASK LEVEL Records insert into DB
			 */
			ProcesslogGenerator
					.Logger("Start : Data availability Metrics data inserting into FAILED_CALLL_TASK_LEVEL_REPORT table");
			String strFailedTestcases = "SELECT TESTCASE_ID,TESTCASE_NAME,METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE,'NA' AS ROOTCAUSE,'NA' AS ISSUE_IN_MODULE,'NA' AS RECOMMENDED_ACTION FROM  TOTAL_CALL_TASK_LEVEL_REPORT WHERE (METRIC_VALUE <= 0 OR METRIC_VALUE IS NULL) AND SUBSTRING(METRIC_CODE,1,2) = '"
					+ AggrigationType
					+ "' AND TESTCASE_ID='"
					+ Testcaseid
					+ "'";

			Statement stFailedTestcases = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsFailedTestcases = stFailedTestcases
					.executeQuery(strFailedTestcases);

			while (rsFailedTestcases.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo.FAILED_CALLL_TASK_LEVEL_REPORT(TESTCASE_ID, TESTCASE_NAME, METRIC_CODE,DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE,ROOTCAUSE,ISSUE_IN_MODULE,RECOMMENDED_ACTION) values (?,?,?,?,?,?,?,?,?,?,?,?)");

				stmt.setString(1,
						(String) rsFailedTestcases.getObject("TESTCASE_ID"));
				stmt.setString(2,
						(String) rsFailedTestcases.getObject("TESTCASE_NAME"));
				stmt.setString(3,
						(String) rsFailedTestcases.getObject("METRIC_CODE"));
				stmt.setInt(4, (int) rsFailedTestcases.getObject("DRIVE_ID"));
				stmt.setString(5, (String) rsFailedTestcases
						.getObject("SOURCE_FILE_NAME"));
				stmt.setInt(6, (int) rsFailedTestcases.getObject("CALL_ID"));
				stmt.setInt(7, (int) rsFailedTestcases.getInt(7));
				stmt.setString(8,
						(String) rsFailedTestcases.getObject("METRIC_CODE"));
				stmt.setInt(9, rsFailedTestcases.getInt(9));
				stmt.setString(10,
						(String) rsFailedTestcases.getObject("ROOTCAUSE"));
				stmt.setString(11,
						(String) rsFailedTestcases.getObject("ISSUE_IN_MODULE"));
				stmt.setString(12, (String) rsFailedTestcases
						.getObject("RECOMMENDED_ACTION"));
				stmt.executeUpdate();
				conn.commit();
			}
			ProcesslogGenerator
					.Logger("End : Data availability Metrics data inserting into FAILED_CALLL_TASK_LEVEL_REPORT table");

		} catch (Exception Ex) {
			isDataAvailableCal = false;
			Ex.printStackTrace();
		}
		return isDataAvailableCal;
	}

	/*
	 * Test case level report
	 */
	public static boolean TestCaseLevelGeneration(Connection conn) {
		boolean isTestCaseLevelReportGen = true;
		ProcesslogGenerator.Logger("Start : Test case level report generation");
		StringBuffer sbTestCasebuffer = new StringBuffer();
		try {
			/*
			 * testcase level report
			 */

			/*
			 * Add Headers to StringBuffer to report in the output file
			 */
			sbTestCasebuffer = LoadConfigurationData.sbHeaders(
					ReadInputFile.properties.getProperty(
							"TestCaseOutputHeaders").split(",").length,
					ReadInputFile.properties
							.getProperty("TestCaseOutputHeaders"));

			String strtestcaselevelreport = "SELECT AA.TESTCASE_ID,AA.TESTCASE_NAME,CASE WHEN (TOTAL_NUMBER_OF_CALLS>0 AND TOTAL_NUMBER_OF_FAILED_CALLS=0 ) THEN 'PASSED' WHEN (TOTAL_NUMBER_OF_CALLS>0 AND TOTAL_NUMBER_OF_FAILED_CALLS>0 ) THEN 'FAILED' ELSE  'NA' END [STATUS], TOTAL_NUMBER_OF_CALLS,TOTAL_NUMBER_OF_FAILED_CALLS ,CASE WHEN (TOTAL_NUMBER_OF_CALLS>0 AND TOTAL_NUMBER_OF_FAILED_CALLS=0 ) THEN 'NA' WHEN (TOTAL_NUMBER_OF_CALLS>0 AND TOTAL_NUMBER_OF_FAILED_CALLS>0 ) THEN 'Refer the Failed-CallTaskLevelReport sheet for more details( CARRIER or CALL/TASK level)' ELSE  'NA' END REFERENCE FROM(SELECT AA.TESTCASE_ID,AA.TESTCASE_NAME,COUNT(AA.METRIC_ID) AS TOTAL_NUMBER_OF_CALLS,COUNT(BB.METRIC_ID) AS TOTAL_NUMBER_OF_FAILED_CALLS FROM TOTAL_CALL_TASK_LEVEL_REPORT AA LEFT JOIN FAILED_CALLL_TASK_LEVEL_REPORT BB ON AA.TESTCASE_ID=BB.TESTCASE_ID AND AA.SOURCE_FILE_NAME=BB.SOURCE_FILE_NAME AND AA.CALL_ID=BB.CALL_ID AND ISNULL(AA.TASK_ID,0)=ISNULL(BB.TASK_ID,0) GROUP BY AA.TESTCASE_ID,AA.TESTCASE_NAME)AA ORDER BY AA.TESTCASE_ID ASC";
			Statement sttestcaselevelreport = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rstestcaselevelreport = sttestcaselevelreport
					.executeQuery(strtestcaselevelreport);

			while (rstestcaselevelreport.next()) {
				sbTestCaseReport.append(rstestcaselevelreport.getObject(
						"TESTCASE_ID").toString()
						+ comm.COMMA
						+ rstestcaselevelreport.getObject("TESTCASE_NAME")
								.toString().trim()
						+ comm.COMMA
						+ rstestcaselevelreport.getObject("STATUS").toString()
						+ comm.COMMA
						+ rstestcaselevelreport.getObject(
								"TOTAL_NUMBER_OF_CALLS").toString()
						+ comm.COMMA
						+ rstestcaselevelreport.getObject(
								"TOTAL_NUMBER_OF_FAILED_CALLS").toString()
						+ comm.COMMA
						+ rstestcaselevelreport.getObject("REFERENCE")
								.toString());

				sbTestCaseReport.append("\n");
			}
			/*
			 * Validate the test cases and Append to StringBuffer to store into
			 * output. Store Testcase Result in diretory specified in property
			 * file
			 */
			sbTestCasebuffer.append(sbTestCaseReport.toString());

			/*
			 * Validate the test cases and Append to StringBuffer to store into
			 * output. Store arrier level Result in diretory specified in
			 * property file
			 */
			String DateTime = CommonVariables.getCurrentTimeStamp();
			FileUtils.writeStringToFile(
					new File(ReadInputFile.properties
							.getProperty("ReportsDirctory")
							+ ReadInputFile.properties
									.getProperty("TestCaseLevel")
							+ "_"
							+ DateTime
							+ ReadInputFile.properties
									.getProperty("OutputFormat")),
					sbTestCasebuffer.toString());
			ProcesslogGenerator
					.Logger("End : Test case level report generation Successfully in "
							+ ReadInputFile.properties
									.getProperty("ReportsDirctory"));

		} catch (Exception ex) {
			isTestCaseLevelReportGen = false;
			// ex.printStackTrace();
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
		}
		return isTestCaseLevelReportGen;
	}

	/*
	 * Call/Task Level Report
	 */
	public static boolean TaskCallLevelGeneration(Connection conn) {
		boolean isTaskCallLevelReportGen = true;
		ProcesslogGenerator.Logger("Start : Task/call level report generation");
		StringBuffer sbCallTaskLevelbuffer = new StringBuffer();
		try {
			/*
			 * Call/Task level report
			 */

			/*
			 * Add Headers to StringBuffer to call/task report in the output
			 * file
			 */
			sbCallTaskLevelbuffer = LoadConfigurationData
					.sbHeaders(
							ReadInputFile.properties.getProperty(
									"CallTaskLevelHeaders").split(",").length,
							ReadInputFile.properties
									.getProperty("CallTaskLevelHeaders"));

			String strCalltaskreport = "SELECT TESTCASE_ID TESTCASE_ID,TESTCASE_NAME TESTCASE_NAME,SOURCE_FILE_NAME SOURCE_FILE_NAME,CALL_ID CALL_ID,TASK_ID TASK_ID,METRIC_VALUE AS COMMENTS,ROOTCAUSE ROOTCAUSE,ISSUE_IN_MODULE ISSUE_IN_MODULE,RECOMMENDED_ACTION RECOMMENDED_ACTION FROM FAILED_CALLL_TASK_LEVEL_REPORT ORDER BY TESTCASE_ID ASC";

			Statement stCalltaskreport = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsCalltaskreport = stCalltaskreport
					.executeQuery(strCalltaskreport);

			while (rsCalltaskreport.next()) {
				sbCallTaskLevelReport.append(rsCalltaskreport.getObject(
						"TESTCASE_ID").toString()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("TESTCASE_NAME")
								.toString().trim()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("SOURCE_FILE_NAME")
								.toString()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("CALL_ID").toString()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("TASK_ID").toString()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("COMMENTS").toString()
								.toString()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("ROOTCAUSE").toString()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("ISSUE_IN_MODULE")
								.toString()
						+ comm.COMMA
						+ rsCalltaskreport.getObject("RECOMMENDED_ACTION")
								.toString());

				sbCallTaskLevelReport.append("\n");
			}

			/*
			 * Validate the test cases and Append to StringBuffer to store into
			 * output. Store call/task level Result in diretory specified in
			 * property file
			 */
			sbCallTaskLevelbuffer.append(sbCallTaskLevelReport.toString());

			/*
			 * Task/Call level report stored
			 */
			String DateTime = CommonVariables.getCurrentTimeStamp();
			FileUtils.writeStringToFile(
					new File(ReadInputFile.properties
							.getProperty("ReportsDirctory")
							+ ReadInputFile.properties
									.getProperty("CallTaskLevel")
							+ "_"
							+ DateTime
							+ ReadInputFile.properties
									.getProperty("OutputFormat")),
					sbCallTaskLevelbuffer.toString());
			System.out.println("Call/Task Result Saved in ---->"
					+ ReadInputFile.properties.getProperty("ReportsDirctory"));
			ProcesslogGenerator
					.Logger("End : Task/call level report generation Successfully in "
							+ ReadInputFile.properties
									.getProperty("ReportsDirctory"));
		} catch (Exception ex) {
			isTaskCallLevelReportGen = false;
			// ex.printStackTrace();
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
		}
		return isTaskCallLevelReportGen;
	}

	/*
	 * Carrier level report
	 */
	public static boolean CarrierLevelGeneration(Connection conn) {
		boolean isCarrierLevelReportGen = true;
		ProcesslogGenerator.Logger("Start : Carrier level report generation");
		StringBuffer sbCarrierLevelbuffer = new StringBuffer();
		try {
			/*
			 * Carrier level report
			 */

			/*
			 * Add Headers to StringBuffer to Carrier level report in the output
			 * file
			 */
			sbCarrierLevelbuffer = LoadConfigurationData.sbHeaders(
					ReadInputFile.properties.getProperty("CarrierLevelHeaders")
							.split(",").length, ReadInputFile.properties
							.getProperty("CarrierLevelHeaders"));

			String strCarrierLevelreport = "SELECT BB.DRIVE_ID DRIVE_ID,BB.CARRIER_NAME CARRIER_NAME,BB.PORT PORT,AA.TESTCASE_ID TESTCASE_ID,COUNT(AA.CALL_ID) AS TOTAL_NUMBER_OF_CALLS,COUNT(CC.CALL_ID) AS TOTAL_NUMBER_OF_FAILED_CALLS FROM TOTAL_CALL_TASK_LEVEL_REPORT AA INNER JOIN  LU_CARRIER_INFO BB ON SUBSTRING(AA.SOURCE_FILE_NAME,20,5)=BB.DRIVE_ID AND SUBSTRING(AA.SOURCE_FILE_NAME,17,2)=BB.PORT LEFT JOIN FAILED_CALLL_TASK_LEVEL_REPORT CC ON AA.TESTCASE_ID=CC.TESTCASE_ID AND AA.SOURCE_FILE_NAME=CC.SOURCE_FILE_NAME AND AA.CALL_ID=CC.CALL_ID AND ISNULL(AA.TASK_ID,0)=ISNULL(CC.TASK_ID,0) GROUP BY BB.DRIVE_ID,BB.CARRIER_NAME,BB.PORT,AA.TESTCASE_ID ORDER BY TESTCASE_ID ASC";

			Statement stCarrierLevelkreport = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsCarrierLevelkreport = stCarrierLevelkreport
					.executeQuery(strCarrierLevelreport);

			while (rsCarrierLevelkreport.next()) {
				sbCarrierLevelReport.append(rsCarrierLevelkreport.getObject(
						"DRIVE_ID").toString()
						+ comm.COMMA
						+ rsCarrierLevelkreport.getObject("CARRIER_NAME")
								.toString()
						+ comm.COMMA
						+ rsCarrierLevelkreport.getObject("PORT").toString()
						+ comm.COMMA
						+ rsCarrierLevelkreport.getObject("TESTCASE_ID")
								.toString()
						+ comm.COMMA
						+ rsCarrierLevelkreport.getObject(
								"TOTAL_NUMBER_OF_CALLS").toString()
						+ comm.COMMA
						+ rsCarrierLevelkreport
								.getObject("TOTAL_NUMBER_OF_FAILED_CALLS")
								.toString().toString());

				sbCarrierLevelReport.append("\n");
			}

			/*
			 * Validate the test cases and Append to StringBuffer to store into
			 * output. Store arrier level Result in diretory specified in
			 * property file
			 */
			sbCarrierLevelbuffer.append(sbCarrierLevelReport.toString());

			/*
			 * Carrier level report stored
			 */
			String DateTime = CommonVariables.getCurrentTimeStamp();
			FileUtils.writeStringToFile(
					new File(ReadInputFile.properties
							.getProperty("ReportsDirctory")
							+ ReadInputFile.properties
									.getProperty("CarrierLevel")
							+ "_"
							+ DateTime
							+ ReadInputFile.properties
									.getProperty("OutputFormat")),
					sbCarrierLevelbuffer.toString());
			System.out.println("Carrier level Result Saved in ---->"
					+ ReadInputFile.properties.getProperty("ReportsDirctory"));
			ProcesslogGenerator
					.Logger("End : Task/call level report generation Successfully in "
							+ ReadInputFile.properties
									.getProperty("ReportsDirctory"));
		} catch (Exception ex) {
			isCarrierLevelReportGen = false;
			// ex.printStackTrace();
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
		}
		return isCarrierLevelReportGen;
	}
}