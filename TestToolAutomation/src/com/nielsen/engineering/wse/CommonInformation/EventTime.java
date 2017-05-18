/**
 * 
 */
package com.nielsen.engineering.wse.CommonInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.nielsen.engineering.wse.ProcesslogGenerator;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class EventTime {

	public static boolean LoadEventInfo(Connection Connection,
			String ParentTable, String InsertTable) {
		boolean IsEventInfoInserted = true;
		ProcesslogGenerator.Logger("Start : AUT Table for Event Info");
		try {
			int DriveID = 0, CallId = 0, TaskID = 0;String EventId = "";
			String SourcecFileName = null;
			Date date;

			Statement stSelectQurey = Connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			String sSelectQuery = "SELECT CASE WHEN COLLECTION_TYPE='V' THEN ' SELECT AA.DRIVE_ID ,AA.SOURCE_FILE_NAME ,AA.CALL_ID ,AA.TASK_ID ,BB.EVENT_ID  ,AA.EVENT_TIME  FROM '+ ' ( SELECT DISTINCT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,NULL AS TASK_ID,MODE,[' + COLUMN_NAME + '] AS EVENT_NAME,MIN(GPS_TIME) AS EVENT_TIME ,' + '''' + TEXTFILE_NAME + ''' AS TEXTFILE_NAME ' + ' FROM ' + TEXTFILE_NAME  + ' GROUP BY DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,MODE, ' + COLUMN_NAME + ' )AA ' + ' LEFT JOIN LU_EVENT_INFO BB  ON AA.EVENT_NAME=BB.EVENT_NAME  AND ( (AA.MODE = BB.MODE)  ' + ' OR (ISNULL(AA.MODE, BB.MODE) IS NULL) OR  (AA.MODE=' + '''' + '-999' + '''' + ' AND BB.MODE IS NOT NULL)' +' OR  (AA.MODE IS NULL AND BB.MODE IS NOT NULL) )  AND AA.TEXTFILE_NAME=BB.TEXTFILE_NAME' ELSE '  SELECT AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,BB.EVENT_ID ,AA.EVENT_TIME FROM '+ ' ( SELECT DISTINCT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,NULL MODE,[' + COLUMN_NAME + '] AS EVENT_NAME,MIN(GPS_TIME) AS EVENT_TIME ,' + '''' + TEXTFILE_NAME + ''' AS TEXTFILE_NAME ' + ' FROM ' + TEXTFILE_NAME + ' GROUP BY DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID, ' + COLUMN_NAME + ' )AA '+ 'LEFT JOIN LU_EVENT_INFO BB  ON AA.EVENT_NAME=BB.EVENT_NAME  AND ( (AA.MODE = BB.MODE)  ' + ' OR (ISNULL(AA.MODE, BB.MODE) IS NULL) OR  (AA.MODE=' + '''' + '-999' + '''' + ' AND BB.MODE IS NOT NULL)' +' OR  (AA.MODE IS NULL AND BB.MODE IS NOT NULL) )  AND AA.TEXTFILE_NAME=BB.TEXTFILE_NAME' END QUERY FROM LU_EVENT_MESSAGE_TABLES UNION ALL SELECT CASE WHEN COLLECTION_TYPE='V' THEN '  SELECT AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,BB.EVENT_ID ,AA.EVENT_TIME FROM '+ ' ( SELECT DISTINCT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,NULL AS TASK_ID,MODE,' + '''' + COLUMN_NAME + ''''+  ' AS EVENT_NAME,'+ 'MIN(' + COLUMN_NAME +') AS EVENT_TIME ,' + '''' + TEXTFILE_NAME + ''' AS TEXTFILE_NAME ' + ' FROM ' + TEXTFILE_NAME + ' GROUP BY DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,MODE, ' + COLUMN_NAME + ' )AA '+ 'LEFT JOIN LU_EVENT_INFO BB  ON AA.EVENT_NAME=BB.EVENT_NAME  AND AA.TEXTFILE_NAME=BB.TEXTFILE_NAME' ELSE '  SELECT AA.DRIVE_ID,AA.SOURCE_FILE_NAME,AA.CALL_ID,AA.TASK_ID,BB.EVENT_ID ,AA.EVENT_TIME FROM '+ ' ( SELECT DISTINCT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,NULL MODE,'+ '''' + COLUMN_NAME + ''''+ ' AS EVENT_NAME,'+ 'MIN(' + COLUMN_NAME +') AS EVENT_TIME ,' + '''' + TEXTFILE_NAME + ''' AS TEXTFILE_NAME ' + ' FROM ' + TEXTFILE_NAME + ' GROUP BY DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID, ' + COLUMN_NAME + ' )AA '+ 'LEFT JOIN LU_EVENT_INFO BB  ON AA.EVENT_NAME=BB.EVENT_NAME    AND AA.TEXTFILE_NAME=BB.TEXTFILE_NAME' END QUERY FROM LU_EVENT_FIELD_TABLES";
			ResultSet rsSelect = stSelectQurey.executeQuery(sSelectQuery);
			while (rsSelect.next()) {

				// Statement st = Connection.createStatement(
				// ResultSet.TYPE_SCROLL_INSENSITIVE,
				// ResultSet.CONCUR_READ_ONLY,
				// ResultSet.HOLD_CURSORS_OVER_COMMIT);
				//
				// String sql =
				// "SELECT A.DRIVE_ID DRIVE_ID,A.SOURCE_FILE_NAME SOURCE_FILE_NAME,A.CALL_ID CALL_ID,A.TASK_ID TASK_ID,B.EVENT_ID EVENTID,A.GPS_TIME GPS_TIME FROM(SELECT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,TASK_STATE_DESC,GPS_TIME FROM	(SELECT DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,TASK_STATE_DESC,GPS_TIME FROM "
				// + ParentTable
				// +
				// "	GROUP BY DRIVE_ID,SOURCE_FILE_NAME,CALL_ID,TASK_ID,TASK_STATE_DESC,GPS_TIME)A where TASK_STATE_DESC IS NOT NULL)A INNER JOIN LU_EventDefinitions B ON A.TASK_STATE_DESC = B.EVENT_NAME";
				// ResultSet rs = st.executeQuery(sql);
				// while (rs.next()) {
				String sQuery = (String) rsSelect.getObject("QUERY");
				
				Statement stquery = Connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY,
						ResultSet.HOLD_CURSORS_OVER_COMMIT);				
				ResultSet rsresult = stquery.executeQuery(sQuery);
				while (rsresult.next()) {
					DriveID = (int) rsresult.getObject("DRIVE_ID");
					SourcecFileName = (String) rsresult
							.getObject("SOURCE_FILE_NAME");
					CallId = (int) rsresult.getObject("CALL_ID");
					TaskID = rsresult.getInt(4);
					EventId = (String) rsresult.getObject("EVENT_ID");
					
					date = (Date) rsresult.getObject("EVENT_TIME");

					PreparedStatement stmt = Connection
							.prepareStatement("INSERT INTO dbo."
									+ InsertTable
									+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_ID,EVENT_ID,EVENT_TIME) values (?, ? , ?, ?, ?,?)");

					stmt.setInt(1, DriveID);
					stmt.setString(2, SourcecFileName);
					stmt.setInt(3, CallId);
					stmt.setInt(4, TaskID);
					stmt.setString(5, EventId);
					stmt.setTimestamp(6, (java.sql.Timestamp) date);
					stmt.executeUpdate();
					Connection.commit();
				}
			}
			// rs.close();
		} catch (Exception ex) {
			IsEventInfoInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		ProcesslogGenerator
				.Logger("End : AUT Table for Event Info successfully");
		return IsEventInfoInserted;

	}
}