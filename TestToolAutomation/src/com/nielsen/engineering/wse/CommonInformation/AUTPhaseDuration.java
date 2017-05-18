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
public class AUTPhaseDuration {
	/*
	 * Calculate Phase Duration
	 */
	public static boolean LoadAUTPhaseDuration(Connection conn,
			String ParentTable, String InsertTable) {
		ProcesslogGenerator.Logger("Start: AUT table for Phase duration");
		boolean isautPhaseduration = true;
		try {
			String phaseduration = "SELECT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,TASK_RESULTS_CODE,PHASE_ID,PHASE_START_TIME,PHASE_END_TIME,DATEDIFF(MS,PHASE_START_TIME,PHASE_END_TIME)  AS PHASE_DURATION FROM(SELECT AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,TASK_RESULTS_CODE,BB.PHASE_ID,BB.PHASE_START_TIME,BB.PHASE_END_TIME,DATEDIFF(MS,BB.PHASE_START_TIME,BB.PHASE_END_TIME)  AS DIFF FROM AUT_TASK_RESULTS AA INNER JOIN(SELECT  AA.COLLECTION_TYPE,AA.PHASE_ID,AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,AA.PHASE_START_TIME,BB.PHASE_END_TIME FROM(SELECT DISTINCT AA.COLLECTION_TYPE,AA.PHASE_ID,BB.DRIVE_ID,BB.SOURCE_FILE_NAME,BB.CALL_ID,BB.TASK_ID,MIN(BB.EVENT_TIME) AS PHASE_START_TIME FROM LU_PHASE_EVENT_CORRELATION AA INNER JOIN AUT_EVENT_TIME BB ON AA.START_EVENT_ID=BB.EVENT_ID GROUP BY AA.COLLECTION_TYPE,AA.PHASE_ID, BB.DRIVE_ID,BB.SOURCE_FILE_NAME,BB.CALL_ID,BB.TASK_ID )AA INNER JOIN(SELECT DISTINCT AA.COLLECTION_TYPE,AA.PHASE_ID,BB.DRIVE_ID,BB.SOURCE_FILE_NAME,BB.CALL_ID,BB.TASK_ID,MIN(BB.EVENT_TIME) AS PHASE_END_TIME FROM LU_PHASE_EVENT_CORRELATION AA INNER JOIN AUT_EVENT_TIME BB ON AA.END_EVENT_ID=BB.EVENT_ID GROUP BY AA.COLLECTION_TYPE,AA.PHASE_ID, BB.DRIVE_ID,BB.SOURCE_FILE_NAME,BB.CALL_ID,BB.TASK_ID )BB ON AA.SOURCE_FILE_NAME=BB.SOURCE_FILE_NAME AND AA.CALL_ID=BB.CALL_ID AND ISNULL(AA.TASK_ID,0)=ISNULL(BB.TASK_ID,0) AND AA.PHASE_ID=BB.PHASE_ID)BB ON AA.SOURCE_FILE_NAME=BB.SOURCE_FILE_NAME AND AA.CALL_ID=BB.CALL_ID AND ISNULL(AA.TASK_ID,0)=ISNULL(BB.TASK_ID,0))AA";

			Statement st2 = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsresult = st2.executeQuery(phaseduration);

			while (rsresult.next()) {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_ID,TASK_RESULTS_CODE,PHASE_ID,PHASE_START_TIME,PHASE_END_TIME,PHASE_DURATION) values (?,?,?,?,?,?,?,?,?)");

				stmt.setInt(1, (int) rsresult.getObject("DRIVE_ID"));
				stmt.setString(2,
						(String) rsresult.getObject("SOURCE_FILE_NAME"));
				stmt.setInt(3, (int) rsresult.getObject("CALL_ID"));
				stmt.setInt(4, (int) rsresult.getInt(4));

				if (!rsresult.wasNull()
						&& rsresult.getObject("TASK_RESULTS_CODE") != null) {
					stmt.setString(5,
							(String) rsresult.getObject("TASK_RESULTS_CODE"));
				} else {

					stmt.setNull(5, java.sql.Types.NVARCHAR);
				}

				if (!rsresult.wasNull()
						&& rsresult.getObject("PHASE_ID") != null) {
					stmt.setString(6, (String) rsresult.getObject("PHASE_ID"));
				} else {

					stmt.setNull(6, java.sql.Types.NVARCHAR);
				}
				if (!rsresult.wasNull()
						&& rsresult.getObject("PHASE_START_TIME") != null) {
					stmt.setTimestamp(7, (java.sql.Timestamp) rsresult
							.getObject("PHASE_START_TIME"));
				} else {

					stmt.setNull(7, java.sql.Types.TIMESTAMP);
				}
				if (!rsresult.wasNull()
						&& rsresult.getObject("PHASE_END_TIME") != null) {
					stmt.setTimestamp(8, (java.sql.Timestamp) rsresult
							.getObject("PHASE_END_TIME"));
				} else {

					stmt.setNull(8, java.sql.Types.TIMESTAMP);
				}
				if (!rsresult.wasNull()
						&& rsresult.getObject("PHASE_DURATION") != null) {
					stmt.setInt(9, (int) rsresult.getObject("PHASE_DURATION"));
				} else {

					stmt.setNull(9, java.sql.Types.INTEGER);
				}

				stmt.executeUpdate();
				conn.commit();
			}
		} catch (Exception Ex) {
			isautPhaseduration = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(Ex));
			// Ex.printStackTrace();
		}
		ProcesslogGenerator
				.Logger("End: AUT table for Phase duration successfully");
		return isautPhaseduration;
	}

}
