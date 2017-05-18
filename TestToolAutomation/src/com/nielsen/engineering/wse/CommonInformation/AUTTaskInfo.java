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
public class AUTTaskInfo {

	public static boolean LoadAUTTaskInfo(Connection conn, String ParentTable,
			String InsertTable) {
		ProcesslogGenerator.Logger("Start: AUT table for Task Info");
		boolean isautTaskinfo = true;
		try {
			String Taskinfo = "SELECT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_NAME,COLLECTION_TYPE,BB.TASK_TYPE_ID FROM(SELECT  DISTINCT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_NAME,'D' AS COLLECTION_TYPE FROM "
					+ ParentTable
					+ " WHERE TASK_END_DESC IS NOT NULL UNION ALL SELECT DISTINCT AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID, COLLECTION_PLATFORM AS TASK_NAME,COLLECTION_TYPE FROM STG_CALL_RESULTS  AA INNER JOIN AUT_DRIVE_INFO BB ON AA.DRIVE_ID=BB.DRIVE_ID AND SUBSTRING(AA.SOURCE_FILE_NAME,17,2)=BB.PORT_ID)AA LEFT JOIN LU_TASK_TYPE BB  ON AA.TASK_NAME=BB.TASK_TYPE";

			Statement st2 = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsresult = st2.executeQuery(Taskinfo);

			while (rsresult.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_NAME,COLLECTION_TYPE,TASK_TYPE_ID) values (?,?,?,?,?,?)");

				stmt.setInt(1, (int) rsresult.getObject("DRIVE_ID"));
				stmt.setString(2,
						(String) rsresult.getObject("SOURCE_FILE_NAME"));
				stmt.setInt(3, (int) rsresult.getObject("CALL_ID"));
				stmt.setString(4, (String) rsresult.getObject("TASK_NAME"));
				stmt.setString(5,
						(String) rsresult.getObject("COLLECTION_TYPE"));
				stmt.setString(6, (String) rsresult.getObject("TASK_TYPE_ID"));

				stmt.executeUpdate();
				conn.commit();
			}
		} catch (Exception Ex) {
			isautTaskinfo = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(Ex));
			// Ex.printStackTrace();
		}
		ProcesslogGenerator.Logger("End: AUT table for Task info successfully");
		return isautTaskinfo;
	}

}
