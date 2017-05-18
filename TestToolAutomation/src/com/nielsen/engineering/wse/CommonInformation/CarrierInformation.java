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
public class CarrierInformation {

	public static boolean LoadCarrierInformation(Connection Connection,
			String ParentTable, String InsertTable) {
		boolean IsCarrierInforamtionInserted = true;
		ProcesslogGenerator.Logger("Start : AUT Table for Carrier Info");
		try {
			int DriveID = 0;
			String Port = null;
			String CarrierName = "";
			Statement st = Connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			String sql = "SELECT DISTINCT DRIVE_ID,SUBSTRING(SOURCE_FILE_NAME,17,2) PORT FROM "
					+ ParentTable + "  GROUP BY DRIVE_ID,SOURCE_FILE_NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				DriveID = (int) rs.getObject("DRIVE_ID");
				Port = (String) rs.getObject("PORT");
				// CarrierName = (String) rs.getObject("CARRIER_NAME");

				String sQuery = "SELECT DISTINCT TOP 1 CARRIER_NAME FROM LU_MarketCarrier_Information WHERE PORT='"
						+ Port + "'";

				Statement st1 = null;
				st1 = Connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY,
						ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ResultSet srs = st1.executeQuery(sQuery);

				while (srs.next()) {
					CarrierName = (String) srs.getObject("CARRIER_NAME");
				}

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, PORT, CARRIER_NAME) values (?, ? , ?)");

				stmt.setInt(1, DriveID);
				stmt.setString(2, Port);
				stmt.setString(3, CarrierName);
				stmt.executeUpdate();
				Connection.commit();
			}
			rs.close();
		} catch (Exception ex) {
			IsCarrierInforamtionInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			//ex.printStackTrace();
		}
		ProcesslogGenerator.Logger("End : AUT Table for Carrier Info");
		return IsCarrierInforamtionInserted;

	}

}
