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
public class DriveInformation {

	public static boolean LoadDriveInformation(Connection Connection,
			String ParentTable, String InsertTable) {
		boolean IsDriveInforamtionInserted = true;
		ProcesslogGenerator.Logger("Start : AUT Table for Drive Information");
		try {
			int DriveID = 0;
			String Port = null;
			String CollectionPlatFrom = "",CollectionType="";
			Statement st = Connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);
			String sql = "SELECT DRIVE_ID DRIVE_ID,SUBSTRING(SOURCE_FILE_NAME,17,2) AS PORT_ID, BB.COLLECTION_TYPE COLLECTION_TYPE,AA.COLLECTION_PLATFORM COLLECTION_PLATFORM FROM "
					+ ParentTable
					+ " AA LEFT JOIN LU_COLLCETION_PLATFORM BB ON AA.COLLECTION_PLATFORM=BB.COLLECTION_PLATFORM";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				DriveID = (int) rs.getObject("DRIVE_ID");
				Port = (String) rs.getObject("PORT_ID");
				CollectionType= (String) rs.getObject("COLLECTION_TYPE");
				CollectionPlatFrom = (String) rs
						.getObject("COLLECTION_PLATFORM");

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo."
								+ InsertTable
								+ " (DRIVE_ID, PORT_ID, COLLECTION_TYPE,COLLECTION_PLATFORM) values (?, ? , ?,?)");

				stmt.setInt(1, DriveID);
				stmt.setString(2, Port);
				stmt.setString(3, CollectionType);
				stmt.setString(4, CollectionPlatFrom);
				stmt.executeUpdate();
				Connection.commit();
			}
			rs.close();

		} catch (Exception ex) {
			IsDriveInforamtionInserted = false;
			ProcesslogGenerator.Logger(ExceptionUtils.getStackTrace(ex));
			// ex.printStackTrace();
		}
		ProcesslogGenerator
				.Logger("End : AUT Table for Drive Information Successfully");
		return IsDriveInforamtionInserted;

	}

}
