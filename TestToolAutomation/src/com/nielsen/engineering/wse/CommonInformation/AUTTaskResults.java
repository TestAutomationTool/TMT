/**
 * 
 */
package com.nielsen.engineering.wse.CommonInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.nielsen.engineering.wse.ProcesslogGenerator;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class AUTTaskResults {
	public static boolean LoadAUTTaskInfo(Connection conn, String ParentTable,
			String InsertTable) {
		ProcesslogGenerator.Logger("Start: AUT table for Task Result Info");
		boolean isautTaskinfo = true;
		try {
			String Taskinfo = "SELECT DISTINCT AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,AA.TASK_RESULT,BB.TASK_RESULTS_CODE FROM(SELECT  DISTINCT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,TASK_END_CAUSE_DESC  AS TASK_RESULT FROM "
					+ ParentTable
					+ " WHERE TASK_END_DESC IS NOT NULL UNION ALL SELECT DISTINCT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,NULL AS TASK_ID,CAST(CALL_RESULT_ID AS VARCHAR) AS TASK_RESULT FROM STG_CALL_RESULTS )AA LEFT JOIN LU_TASK_RESULTS BB ON AA.TASK_RESULT=BB.TASK_RESULTS";

			Statement st2 = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsresult = st2.executeQuery(Taskinfo);

			while (rsresult.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_ID,TASK_RESULT,TASK_RESULTS_CODE) values (?,?,?,?,?,?)");

				stmt.setInt(1, (int) rsresult.getObject("DRIVE_ID"));
				stmt.setString(2,
						(String) rsresult.getObject("SOURCE_FILE_NAME"));
				stmt.setInt(3, (int) rsresult.getObject("CALL_ID"));
				stmt.setInt(4, (int) rsresult.getInt(4));
				stmt.setString(5,
						(String) rsresult.getObject("TASK_RESULT"));
				stmt.setString(6, (String) rsresult.getObject("TASK_RESULTS_CODE"));
				stmt.executeUpdate();
				conn.commit();
			}
		} catch (Exception Ex) {
			isautTaskinfo = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(Ex));
			// Ex.printStackTrace();
		}
		ProcesslogGenerator
				.Logger("End: AUT table for Task result info successfully");
		return isautTaskinfo;
	}

}
