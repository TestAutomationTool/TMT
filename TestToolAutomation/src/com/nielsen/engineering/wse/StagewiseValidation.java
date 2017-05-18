/**
 * 
 */
package com.nielsen.engineering.wse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class StagewiseValidation {

	public static boolean StagewiseValidator(Connection con) {
		ProcesslogGenerator
				.Logger("Start: Stage wise validation for test cases");
		boolean isStagewiseValidation = true;
		try {
			CallableStatement cstmt = null;
			String SQL ="{call usp_Statewise_TestCase_Validation ()}";
			//Statement stmt = con.createStatement();
			//cstmt = con.prepareCall (SQL);
			boolean results = cstmt.execute(SQL);
			int rsCount = 0;

			// Loop through the available result sets.
			do {
				if (results) {
					ResultSet rs = cstmt.getResultSet();
					rsCount++;

					// Show data from the result set.
					System.out.println("RESULT SET #" + rsCount);
					while (rs.next()) {
						System.out.println(rs.getString("DRIVE_ID"));
					}
					rs.close();
				}
				System.out.println();
				results = cstmt.getMoreResults();
			} while (results);
			cstmt.close();

		} catch (Exception ex) {
			isStagewiseValidation = false;
			ex.printStackTrace();
		}
		ProcesslogGenerator.Logger("End: Stage wise validation for test cases");
		return isStagewiseValidation;
	}

}
