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
public class DeviceTechnologyInformation {

	public static boolean LoadDeviceTechnologyInformation(
			Connection Connection, String ParentTable, String InsertTable) {
		boolean IsDeviceTechInforamtionInserted = true;
		ProcesslogGenerator.Logger("Start : AUT Table for Device tech info");
		try {
			int DriveID = 0;
			String Port = null;
			String DeviceTech = "", SupportedTech = "";
			Statement st = Connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			String sql = "SELECT DISTINCT DRIVE_ID,SUBSTRING(SOURCE_FILE_NAME,17,2) PORT,DEVICE_TECHNOLOGY_NAME FROM "
					+ ParentTable
					+ " GROUP BY DRIVE_ID,SOURCE_FILE_NAME,DEVICE_TECHNOLOGY_NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				DriveID = (int) rs.getObject("DRIVE_ID");
				Port = (String) rs.getObject("PORT");
				DeviceTech = (String) rs.getObject("DEVICE_TECHNOLOGY_NAME");

				Statement st1 = null;
				
				String sQuery = "SELECT SUPPORTED_TECHNOLOGY FROM LU_MarketCarrier_Information WHERE DEVICE_TECHNOLOGY_NAME LIKE '%"
						+ DeviceTech + "%'";
				st1 = Connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY,
						ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ResultSet srs = st1.executeQuery(sQuery);

				while (srs.next()) {
					SupportedTech = (String) srs
							.getObject("SUPPORTED_TECHNOLOGY");

					PreparedStatement stmt = Connection
							.prepareStatement("INSERT INTO dbo.DEVICE_TECHNOLOGY_INFORMATION (DRIVE_ID, PORT, DEVICE_TECHNOLOGY_NAME, SUPPORTED_TECHNOLOGY) values (?, ?, ?, ?)");

					stmt.setInt(1, DriveID);
					stmt.setString(2, Port);
					stmt.setString(3, DeviceTech);
					stmt.setString(4, SupportedTech);
					stmt.executeUpdate();
					Connection.commit();
				}

				//rs.close();
			}

		} catch (Exception ex) {
			IsDeviceTechInforamtionInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			//ex.printStackTrace();
		}
		ProcesslogGenerator.Logger("End : AUT Table for Device tech info");
		return IsDeviceTechInforamtionInserted;

	}

}
