/**
 * 
 */
package com.nielsen.engineering.wse.CommonInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.nielsen.engineering.wse.ProcesslogGenerator;
import com.nielsen.engineering.wse.ToStringClasses.FunctionParameters;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class MetricSampleCount {

	public static boolean LoadMetricAggrigation(Connection Connection,
			String ParentTable, String InsertTable) {
		boolean IsMetricInserted = true;
		ProcesslogGenerator
				.Logger("Start : AUT Table for Metric aggrigation data");
		try {

			/*
			 * to insert data into Metric agggrigation
			 */

			List<FunctionParameters> funparams = new ArrayList<FunctionParameters>();
			funparams = FunctionParams(Connection);

			for (FunctionParameters strmetric : funparams) {
				boolean isAggrigatedDataInserted = MetricConstraintCal(
						strmetric.getEventPhaseID(),
						strmetric.getTextFilename(), strmetric.getMetric(),
						Connection, ParentTable, InsertTable,
						strmetric.getConditionClause(),
						strmetric.getColumnName());
				if (isAggrigatedDataInserted) {
					ProcesslogGenerator.Logger("Data inserted successfuly for "
							+ strmetric.getMetric()
							+ "In to Aggrigated Data Table");
					System.out.println("Data inserted successfuly for "
							+ strmetric.getMetric()
							+ "In to Aggrigated Data Table");
				}

			}
		} catch (Exception ex) {
			IsMetricInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		ProcesslogGenerator
				.Logger("End : AUT Table for Metric aggrigation data successfully");
		return IsMetricInserted;

	}

	private static List<FunctionParameters> FunctionParams(Connection Conn) {
		List<FunctionParameters> functionparam = new ArrayList<FunctionParameters>();
		/*
		 * Call Temp Table creation method for testcaes execution
		 */
		ProcesslogGenerator.Logger("Start : generating function parameters");
		boolean isTempTableCreated = TestCaseMetricGeneration(Conn);

		try {
			if (isTempTableCreated) {
				String strFunctionParameters = "SELECT * FROM QUERY_FUNCTION_PARAMETERS ";
				Statement stFunctionParameters = Conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY,
						ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ResultSet rsresult = stFunctionParameters
						.executeQuery(strFunctionParameters);
				while (rsresult.next()) {
					FunctionParameters funParams = new FunctionParameters();
					funParams.setEventPhaseID((String) rsresult
							.getObject("EVENT_PHASE_ID"));
					funParams.setTextFilename((String) rsresult
							.getObject("TEXTFILE_NAME"));
					funParams.setMetric((String) rsresult.getObject("METRICS"));
					funParams.setConditionClause((String) rsresult
							.getObject("WHERE_CLAUSE"));
					funParams.setColumnName((String) rsresult
							.getObject("DATETIME_FIELD"));
					functionparam.add(funParams);
				}
			} else {
				return null;

			}

		} catch (Exception ex) {
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
		}
		ProcesslogGenerator.Logger("End : generating function parameters");
		return functionparam;
	}

	@SuppressWarnings("unused")
	private static boolean MetricConstraintCal(String phaseID,
			String textFileName, String MetricVal, Connection conn,
			String ParentTable, String InsertTable, String WhereClause,
			String ColumnName) {
		boolean isMetricAggriInsert = true;
		ProcesslogGenerator
				.Logger("Start : Inserting data into TEMP_AGG_DATA to perform the test case with metric name and where clause");
		try {

			String truncateTempTable = "IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TEMP_AGG_DATA]') AND type in (N'U'))"

					+ "\r\n" + " DROP TABLE [dbo].[TEMP_AGG_DATA]";

			Statement st3 = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int Truncate = st3.executeUpdate(truncateTempTable);

			String strFunctionParameters = " SELECT * INTO  TEMP_AGG_DATA FROM (SELECT AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,AA.PHASE_ID,"
					+ MetricVal
					+ "FROM AUT_PHASE_DURATION AA INNER JOIN "
					+ textFileName
					+ " BB ON AA.DRIVE_ID=BB.DRIVE_ID AND AA.SOURCE_FILE_NAME=BB.SOURCE_FILE_NAME AND BB."
					+ ColumnName
					+ ">=AA.PHASE_START_TIME AND BB."
					+ ColumnName
					+ "<=AA.PHASE_END_TIME WHERE AA.PHASE_ID='"
					+ phaseID
					+ "' AND  "
					+ WhereClause
					+ " GROUP BY AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,AA.PHASE_ID)AA ";
			Statement stFunctionParameters = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int rsresult = stFunctionParameters
					.executeUpdate(strFunctionParameters);

			String insertquery = "SELECT 'SELECT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,' + '''' + COLUMN_NAME + ''' AS METRIC_CODE,['+ COLUMN_NAME + '] AS METRIC_VALUE FROM TEMP_AGG_DATA ' AS SELECT_QUERY FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='TEMP_AGG_DATA' AND COLUMN_NAME NOT IN ('DRIVE_ID','SOURCE_FILE_NAME','CALL_ID','TASK_ID','PHASE_ID' )";

			Statement stnsertquery = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			ResultSet rsInsertQuery = stnsertquery.executeQuery(insertquery);

			while (rsInsertQuery.next()) {

				String subinsertqry = (String) rsInsertQuery
						.getObject("SELECT_QUERY");
				ProcesslogGenerator
						.Logger("SELECT Query for calaulating the metric aggrigation "
								+ subinsertqry);
				Statement stsubinsertqry = conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY,
						ResultSet.HOLD_CURSORS_OVER_COMMIT);

				ResultSet rsselectresult = stsubinsertqry
						.executeQuery(subinsertqry);

				ProcesslogGenerator
						.Logger("Start :Inserting the values into the table "
								+ InsertTable);
				while (rsselectresult.next()) {
					PreparedStatement stmt = conn
							.prepareStatement("INSERT INTO dbo."
									+ InsertTable
									+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_ID,METRIC_ID,METRIC_VALUE) values (?,?,?,?,?,?)");

					stmt.setInt(1, (int) rsselectresult.getObject("DRIVE_ID"));
					stmt.setString(2, (String) rsselectresult
							.getObject("SOURCE_FILE_NAME"));
					stmt.setInt(3, (int) rsselectresult.getObject("CALL_ID"));
					stmt.setInt(4, (int) rsselectresult.getInt(4));
					stmt.setString(5,
							(String) rsselectresult.getObject("METRIC_CODE"));
					stmt.setInt(6, rsselectresult.getInt(6));
					stmt.executeUpdate();
					conn.commit();
				}
				ProcesslogGenerator
						.Logger("End :Inserting the values into the table "
								+ InsertTable);
				// int isubqueryinsert =
				// stsubinsertqry.executeUpdate(subinsertqry);
			}

		} catch (Exception ex) {
			isMetricAggriInsert = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		return isMetricAggriInsert;
	}

	@SuppressWarnings("unused")
	private static boolean TestCaseMetricGeneration(Connection Conn) {
		boolean isTestCaseMetric = true;
		try {

			String truncateTempTable = "IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TESTCASE_METRICS]') AND type in (N'U'))"

					+ "\r\n" + " DROP TABLE [dbo].[TESTCASE_METRICS]";

			Statement st3 = Conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int Truncate = st3.executeUpdate(truncateTempTable);

			String truncateTempTable1 = "IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[QUERY_FUNCTION_PARAMETERS]') AND type in (N'U'))"

					+ "\r\n" + " DROP TABLE [dbo].[QUERY_FUNCTION_PARAMETERS]";

			Statement st4 = Conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int Truncate1 = st4.executeUpdate(truncateTempTable1);

			ProcesslogGenerator
					.Logger("Start: Generating the test case metric table query");
			String TESTCASE_METRICS = "SELECT * INTO TESTCASE_METRICS FROM(SELECT AA.*,CC.METRIC_NAME ,CASE WHEN AA.AGGREATION_TYPE_ID='09' THEN 'MIN(' + CC.METRIC_NAME + ') AS '+'''04'+AA.TASK_TYPE_ID+AA.TASK_RESULTS_ID+AA.EVENT_PHASE_ID+AA.EVENT_STATE_ID+AA.TECHNOLOGY_MODE_ID+AA.METRIC_ID+AA.TEXTFILE_ID+''','+'Max(' + CC.METRIC_NAME + ') AS '+'''05'+AA.TASK_TYPE_ID+AA.TASK_RESULTS_ID+AA.EVENT_PHASE_ID+AA.EVENT_STATE_ID+AA.TECHNOLOGY_MODE_ID+AA.METRIC_ID+AA.TEXTFILE_ID+'''' WHEN AA.AGGREATION_TYPE_ID='0A' THEN ' COUNT(ISNULL(' + 'BB.' + CC.METRIC_NAME + ',0)) AS '+''''+AA.AGGREATION_TYPE_ID+AA.TASK_TYPE_ID+AA.TASK_RESULTS_ID+AA.EVENT_PHASE_ID+AA.EVENT_STATE_ID+AA.TECHNOLOGY_MODE_ID+AA.METRIC_ID+AA.TEXTFILE_ID+'''' ELSE BB.AGGREAGATION_TYPE + '(' + CC.METRIC_NAME + ') AS '+''''+AA.AGGREATION_TYPE_ID+AA.TASK_TYPE_ID+AA.TASK_RESULTS_ID+AA.EVENT_PHASE_ID+AA.EVENT_STATE_ID+AA.TECHNOLOGY_MODE_ID+AA.METRIC_ID+AA.TEXTFILE_ID+'''' END  AS METRIC_CAL,DD.TEXTFILE_NAME FROM TESTCASES AA INNER JOIN LU_AGREGATION_TYPE BB ON AA.AGGREATION_TYPE_ID=BB.AGGREGATION_ID INNER JOIN LU_METRIC_NAME CC ON AA.METRIC_ID=CC.METRIC_ID INNER JOIN LU_TEXTFILE_NAME DD ON AA.TEXTFILE_ID=DD.TEXTFILE_ID )AA";

			Statement stTESTCASE_METRICS = Conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int result = stTESTCASE_METRICS.executeUpdate(TESTCASE_METRICS);
			ProcesslogGenerator
					.Logger("End: Generating the test case metric table query");
			ProcesslogGenerator
					.Logger("Start: Generating the table to build Function query parameters to get text file name and where clause and mtrics");
			String strTestMetric = "SELECT * INTO QUERY_FUNCTION_PARAMETERS FROM(SELECT AA.*,BB.COLUMN_NAME AS DATETIME_FIELD FROM(SELECT DISTINCT TM2.EVENT_PHASE_ID EVENT_PHASE_ID,TM2.TEXTFILE_NAME TEXTFILE_NAME, '['+ TM2.METRIC_NAME + '] IS NOT NULL' AS WHERE_CLAUSE,SUBSTRING((SELECT ','+TM1.METRIC_CAL  AS [text()] FROM (SELECT * FROM dbo.TESTCASE_METRICS WHERE AGGREATION_TYPE_ID<>'0A') TM1 WHERE  TM1.TEXTFILE_ID = TM2.TEXTFILE_ID  AND TM1.EVENT_PHASE_ID=TM2.EVENT_PHASE_ID AND TM1.METRIC_ID=TM2.METRIC_ID ORDER BY TM1.EVENT_PHASE_ID,TM1.TEXTFILE_ID FOR XML PATH ('')), 2, 50000) [METRICS] From dbo.TESTCASE_METRICS  TM2 WHERE TM2.AGGREATION_TYPE_ID<>'0A' UNION SELECT DISTINCT TM2.EVENT_PHASE_ID EVENT_PHASE_ID,TM2.TEXTFILE_NAME TEXTFILE_NAME, 'BB.'+ '['+ TM2.METRIC_NAME + '] IS NULL' AS WHERE_CLAUSE, SUBSTRING((SELECT ','+TM1.METRIC_CAL  AS [text()] FROM (SELECT * FROM dbo.TESTCASE_METRICS WHERE AGGREATION_TYPE_ID='0A') TM1 WHERE TM1.TEXTFILE_ID = TM2.TEXTFILE_ID  AND TM1.EVENT_PHASE_ID=TM2.EVENT_PHASE_ID AND TM1.METRIC_ID=TM2.METRIC_ID ORDER BY TM1.EVENT_PHASE_ID,TM1.TEXTFILE_ID FOR XML PATH ('')), 2, 50000) [METRICS] From dbo.TESTCASE_METRICS  TM2 WHERE TM2.AGGREATION_TYPE_ID='0A')AA INNER JOIN LU_DATETIME_FIELDS BB ON AA.TEXTFILE_NAME=BB.TEXTFILE_NAME)AA";

			Statement stTestMetric = Conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			int iresult = stTestMetric.executeUpdate(strTestMetric);
			ProcesslogGenerator
					.Logger("End: Generating the table to build Function query parameters to get text file name and where clause and mtrics");
		} catch (Exception ex) {
			isTestCaseMetric = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		return isTestCaseMetric;

	}
}
