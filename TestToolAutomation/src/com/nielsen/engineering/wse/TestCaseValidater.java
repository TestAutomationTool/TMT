/**
 * 
 */
package com.nielsen.engineering.wse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class TestCaseValidater {

	public static boolean TestCaseValidate(Connection conn) {
		boolean isTestCaseValidate = true;

		String AggrigationType, Testcaseid = null;
		try {

			/*
			 * Get Aggrigation type to insert Data accordingly
			 */
			String strTestcases = "SELECT AGGREATION_TYPE_ID,TESTCASE_ID FROM TESTCASES ";

			Statement stTestcases = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			ResultSet rsTestcase = stTestcases.executeQuery(strTestcases);
			while (rsTestcase.next()) {
				AggrigationType = (String) rsTestcase
						.getObject("AGGREATION_TYPE_ID");
				Testcaseid = (String) rsTestcase.getObject("TESTCASE_ID");

				if (AggrigationType.equalsIgnoreCase("09")) {
					/*
					 * Total Call task level details insert into DB * Failed
					 * Metric ranges insert into DB
					 */
					ReportGeneration.RangeGeneration(conn, AggrigationType,
							Testcaseid);

				} else if (AggrigationType.equalsIgnoreCase("0A")) {
					/*
					 * Null Check value insert into DB failed calls insert into
					 * db
					 */
					ReportGeneration.NullGeneration(conn, AggrigationType,
							Testcaseid);
				}

				else {
					/*
					 * Data availability details
					 */
					ReportGeneration.DataAvailabilityGeneration(conn,
							AggrigationType, Testcaseid);
				}

			}

			/*
			 * testcase level report
			 */

			ReportGeneration.TestCaseLevelGeneration(conn);

			/*
			 * Call/Task level report
			 */
			ReportGeneration.TaskCallLevelGeneration(conn);

			/*
			 * Carrier level report
			 */
			ReportGeneration.CarrierLevelGeneration(conn);

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return isTestCaseValidate;
	}
}
