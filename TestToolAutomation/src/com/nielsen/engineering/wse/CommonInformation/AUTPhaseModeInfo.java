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
public class AUTPhaseModeInfo {
	public static boolean LoadAUTPhaseMode(Connection conn, String ParentTable,
			String InsertTable) {
		boolean isAUTmodeinfo = true;
		ProcesslogGenerator.Logger("Start : AUT Table for Phase Mode info");
		try {
			String phasedmode = "SELECT AA.DRIVE_ID ,AA.SOURCE_FILE_NAME ,AA.CALL_ID ,AA.TASK_ID,DEFAULT_MODE_ID,AA.PHASE_ID,AA.TECHNOLOGY_MODE_DESC,BB.TECHNOLOGY_MODE_ID FROM(SELECT DISTINCT AA.DRIVE_ID ,AA.SOURCE_FILE_NAME ,AA.CALL_ID ,AA.TASK_ID,'00' AS DEFAULT_MODE_ID,AA.TECHNOLOGY_MODE_DESC,BB.PHASE_ID FROM (SELECT DRIVE_ID ,SOURCE_FILE_NAME ,CALL_ID ,TASK_ID,GPS_TIME,TECHNOLOGY_MODE_DESC FROM "
					+ ParentTable
					+ " UNION ALL SELECT DRIVE_ID ,SOURCE_FILE_NAME ,CALL_ID ,NULL AS TASK_ID,GPS_TIME,MODE FROM STG_CALL_DATA)AA INNER JOIN AUT_PHASE_DURATION BB ON AA.DRIVE_ID=BB.DRIVE_ID AND AA.SOURCE_FILE_NAME=BB.SOURCE_FILE_NAME AND AA.GPS_TIME>=BB.PHASE_START_TIME AND AA.GPS_TIME<=BB.PHASE_END_TIME)AA LEFT JOIN LU_TECHNOLOGY_MODE BB ON AA.TECHNOLOGY_MODE_DESC=BB.TECHNOLOGY_MODE";
			Statement stphasedmode = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsphasedmode = stphasedmode.executeQuery(phasedmode);

			while (rsphasedmode.next()) {

				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, SOURCE_FILE_NAME, CALL_ID,TASK_ID,DEFAULT_MODE_ID,PHASE_ID,TECHNOLOGY_MODE_DESC,TECHNOLOGY_MODE_ID) values (?,?,?,?,?,?,?,?)");

				stmt.setInt(1, (int) rsphasedmode.getObject("DRIVE_ID"));
				stmt.setString(2,
						(String) rsphasedmode.getObject("SOURCE_FILE_NAME"));
				stmt.setInt(3, (int) rsphasedmode.getObject("CALL_ID"));

				if (!rsphasedmode.wasNull()) {
					stmt.setInt(4, (int) rsphasedmode.getInt(4));
				} else {
					stmt.setNull(4, java.sql.Types.NVARCHAR);
				}

				stmt.setString(5,
						(String) rsphasedmode.getObject("DEFAULT_MODE_ID"));

				stmt.setString(6, (String) rsphasedmode.getObject("PHASE_ID"));

				if (!rsphasedmode.wasNull()
						&& rsphasedmode.getObject("TECHNOLOGY_MODE_DESC") != null) {
					stmt.setString(7, (String) rsphasedmode
							.getObject("TECHNOLOGY_MODE_DESC"));
				} else {

					stmt.setNull(7, java.sql.Types.NVARCHAR);
				}

				if (!rsphasedmode.wasNull()
						&& rsphasedmode.getObject("TECHNOLOGY_MODE_ID") != null) {
					stmt.setString(8, (String) rsphasedmode
							.getObject("TECHNOLOGY_MODE_ID"));
				} else {

					stmt.setNull(8, java.sql.Types.NVARCHAR);
				}

				stmt.executeUpdate();
				conn.commit();
			}
		} catch (Exception e) {
			isAUTmodeinfo = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(e));
			// e.printStackTrace();
			// TODO: handle exception
		}
		ProcesslogGenerator
				.Logger("End : AUT Table for Phase Mode info Successfully");
		return isAUTmodeinfo;
	}
}
