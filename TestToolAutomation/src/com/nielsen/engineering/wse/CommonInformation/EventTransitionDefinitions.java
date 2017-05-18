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
public class EventTransitionDefinitions {

	@SuppressWarnings("unused")
	public static boolean LoadEventTransInfo(Connection Connection,
			String ParentTable, String InsertTable) {
		boolean IsEventTransInfoInserted = true;
		ProcesslogGenerator.Logger("Start : AUT Table for Event Transition info");
		try {
			int DriveID = 0, CallId = 0, TaskID = 0, EventTransID = 0;
			String SourcecFileName = null;
			int Duration;

			Statement st = Connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			String SetupStartTOTrafficStart = "SELECT * INTO SetupStartTOTrafficStart FROM (SELECT DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,MIN(GPS_TIME) GPS_TIME,'Setup Start-Traffic Start' EventTransID FROM STG_LTE_TASK_STATE_DATA WHERE TASK_STATE_DESC LIKE '%Setup Start%' GROUP BY DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME UNION SELECT DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,MIN(GPS_TIME) GPS_TIME,'Setup Start-Traffic Start' EventTransID FROM STG_LTE_TASK_STATE_DATA WHERE TASK_STATE_DESC LIKE '%Traffic Start%' GROUP BY DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME)A";
			int rs = st.executeUpdate(SetupStartTOTrafficStart);

			String TrafficStartTOSuccess = "SELECT * INTO TrafficStartTOSuccess FROM (SELECT DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,MIN(GPS_TIME) GPS_TIME,'Traffic Start-Success' EventTransID FROM STG_LTE_TASK_STATE_DATA WHERE TASK_STATE_DESC LIKE '%Traffic Start%' GROUP BY DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME UNION SELECT DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,MIN(GPS_TIME) GPS_TIME,'Traffic Start-Success' EventTransID FROM STG_LTE_TASK_STATE_DATA WHERE TASK_STATE_DESC LIKE '%success%' GROUP BY DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME)B";
			int rs1 = st.executeUpdate(TrafficStartTOSuccess);

			String Event_Trans_Definitions = " SELECT * INTO Event_Trans_Definitions FROM (SELECT DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,DATEDIFF(ms,MIN(GPS_TIME),MAX(GPS_TIME)) AS Duration,EventTransID FROM SetupStartTOTrafficStart GROUP BY DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,EventTransID UNION SELECT DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,DATEDIFF(ms,MIN(GPS_TIME),MAX(GPS_TIME)) AS Duration,EventTransID FROM TrafficStartTOSuccess GROUP BY DRIVE_ID,CALL_ID,TASK_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,EventTransID)C";
			int rs2 = st.executeUpdate(Event_Trans_Definitions);

			String Event_Trans_Definitions_Final = "SELECT A.DRIVE_ID DRIVE_ID,A.SOURCE_FILE_NAME SOURCE_FILE_NAME,A.CALL_ID CALL_ID,A.TASK_ID TASK_ID,B.EVENT_TRANSITION_ID EVENT_TRANSITION_ID,A.Duration Duration FROM Event_Trans_Definitions A INNER JOIN LU_Event_Transition_Definitions B ON a.EventTransID=b.EVENT_TRANSITION_NAME";
			ResultSet rs3 = st.executeQuery(Event_Trans_Definitions_Final);

			while (rs3.next()) {

				DriveID = (int) rs3.getObject("DRIVE_ID");
				CallId = (int) rs3.getObject("CALL_ID");
				TaskID = rs3.getInt(4);
				EventTransID = (int) rs3.getObject("EVENT_TRANSITION_ID");
				SourcecFileName = (String) rs3.getObject("SOURCE_FILE_NAME");
				Duration = (int) rs3.getObject("Duration");

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_ID,EVENT_TRANSITION_ID,[EVENT_TRANSITION DURATION(msec)]) values (?, ? , ?, ?, ?,?)");

				stmt.setInt(1, DriveID);
				stmt.setString(2, SourcecFileName);
				stmt.setInt(3, CallId);
				stmt.setInt(4, TaskID);
				stmt.setInt(5, EventTransID);
				stmt.setInt(6, Duration);
				stmt.executeUpdate();
				Connection.commit();
			}
			//rs3.close();
			String TruncateTables = "DROP  TABLE SetupStartTOTrafficStart"
					+ "\r\n" + "DROP  TABLE TrafficStartTOSuccess " + "\r\n"
					+ "DROP  TABLE Event_Trans_Definitions";
			int Truncate = st.executeUpdate(TruncateTables);

		} catch (Exception ex) {
			IsEventTransInfoInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			//ex.printStackTrace();
		}
		ProcesslogGenerator.Logger("End : AUT Table for Event Transition info successfully");
		return IsEventTransInfoInserted;

	}
}
