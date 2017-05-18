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
public class ResultInformation {

	public static boolean LoadResultInformation(Connection Connection,
			String ParentTable, String InsertTable) {
		boolean IsResultInfoInserted = true;
		ProcesslogGenerator.Logger("Start : AUT Table for Result info");
		try {
			int DriveID = 0, CallId = 0, TaskID = 0, ResultId = 0;
			String SourcecFileName = null;
			Statement st = Connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			String sql = "SELECT A.DRIVE_ID DRIVE_ID,A.SOURCE_FILE_NAME SOURCE_FILE_NAME,A.CALL_ID CALL_ID,A.TASK_ID TASK_ID,B.ResultID RESULTID FROM (SELECT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,TASK_END_DESC FROM (SELECT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,CASE WHEN ISNULL(TASK_END_DESC,'') = '' THEN NULL ELSE TASK_END_DESC END AS TASK_END_DESC FROM "
					+ ParentTable
					+ " GROUP BY DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,TASK_END_DESC)A WHERE TASK_END_DESC IS NOT NULL)A INNER JOIN LU_ResultInformation B ON A.TASK_END_DESC = B.ResultName";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				DriveID = (int) rs.getObject("DRIVE_ID");
				CallId = (int) rs.getObject("CALL_ID");
				TaskID = rs.getInt(4);
				ResultId = (int) rs.getObject("RESULTID");
				SourcecFileName = (String) rs.getObject("SOURCE_FILE_NAME");

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_ID,RESULT_ID) values (?, ? , ?, ?, ?)");

				stmt.setInt(1, DriveID);
				stmt.setString(2, SourcecFileName);
				stmt.setInt(3, CallId);
				stmt.setInt(4, TaskID);
				stmt.setInt(5, ResultId);
				stmt.executeUpdate();
				Connection.commit();
			}
			rs.close();
		} catch (Exception ex) {
			IsResultInfoInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			//ex.printStackTrace();
		}
		ProcesslogGenerator.Logger("End : AUT Table for Result info");
		return IsResultInfoInserted;

	}
}
