/**
 * 
 */
package com.nielsen.engineering.wse.DriveDataLoading;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.nielsen.engineering.wse.ToStringClasses.DriveIdInformation;
import com.nielsen.engineering.wse.ToStringClasses.LU_Aggrigation_Types;
import com.nielsen.engineering.wse.ToStringClasses.LU_Collection_Type;
import com.nielsen.engineering.wse.ToStringClasses.LU_DateTime_Fields;
import com.nielsen.engineering.wse.ToStringClasses.LU_Entity_Type;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Definitions;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Info;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Phase;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Phase_Correlations;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_State;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Tables;
import com.nielsen.engineering.wse.ToStringClasses.LU_Event_Transition_Definitions;
import com.nielsen.engineering.wse.ToStringClasses.LU_MetricDefinitions;
import com.nielsen.engineering.wse.ToStringClasses.LU_MetricValidRanges;
import com.nielsen.engineering.wse.ToStringClasses.LU_Metric_Definitions;
import com.nielsen.engineering.wse.ToStringClasses.LU_Metric_Names;
import com.nielsen.engineering.wse.ToStringClasses.LU_Result_Information;
import com.nielsen.engineering.wse.ToStringClasses.LU_Task_Results;
import com.nielsen.engineering.wse.ToStringClasses.LU_Task_Types;
import com.nielsen.engineering.wse.ToStringClasses.LU_Technology_Mode;
import com.nielsen.engineering.wse.ToStringClasses.LU_TextFile_Collection_Type;
import com.nielsen.engineering.wse.ToStringClasses.LU_Textfile_Name;
import com.nielsen.engineering.wse.ToStringClasses.MD_TestCase_Status;
import com.nielsen.engineering.wse.ToStringClasses.MarketCarrierInformation;
import com.nielsen.engineering.wse.ToStringClasses.TestCases;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class ConfigurationDataDBLoading {

	public static boolean LoadMarketCarrierInfo(Connection Connection,
			java.util.List<MarketCarrierInformation> marketCarrierInfo) {
		boolean IsDriveInforamtionInserted = true;
		try {
			for (MarketCarrierInformation Mapping : marketCarrierInfo) {
				String SupportTech = Mapping.getSUPPORTED_TECHNOLOGY();
				for (String TechName : SupportTech.split(",")) {
					PreparedStatement stmt = Connection
							.prepareStatement("INSERT INTO dbo.LU_MarketCarrier_Information (PORT, CARRIER_NAME, DEVICE_TECHNOLOGY_NAME, SUPPORTED_TECHNOLOGY) values (?, ?, ?, ?)");
					stmt.setString(1, Mapping.getPORT());
					stmt.setString(2, Mapping.getCARRIER_NAME());
					stmt.setString(3, Mapping.getDEVICE_TECHNOLOGY_NAME());
					stmt.setString(4, TechName);
					stmt.executeUpdate();
					Connection.commit();
				}
			}

		} catch (Exception ex) {
			IsDriveInforamtionInserted = false;
			ex.printStackTrace();
		}
		return IsDriveInforamtionInserted;

	}

	public static boolean LoadDriveIdInfo(Connection Connection,
			java.util.List<DriveIdInformation> DriveIdInfo) {
		boolean IsDriveInforamtionInserted = true;
		try {

			for (DriveIdInformation Driveinfo : DriveIdInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_DriveID_Information (DRIVEID, COLLECTION_TYPE) values (?, ?)");
				stmt.setInt(1, Integer.parseInt(Driveinfo.getDRIVEID()));
				stmt.setString(2, Driveinfo.getCOLLECTION_TYPE());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsDriveInforamtionInserted = false;
			ex.printStackTrace();
		}
		return IsDriveInforamtionInserted;

	}

	public static boolean LoadResultInfo(Connection Connection,
			java.util.List<LU_Result_Information> ResultInfo) {
		boolean IsResultInforamtionInserted = true;
		try {

			for (LU_Result_Information sResultinfo : ResultInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_ResultInformation (ResultID, CollectionTypeIND,ResultName) values (?, ?,?)");
				stmt.setInt(1, Integer.parseInt(sResultinfo.getResultID()));
				stmt.setString(2, sResultinfo.getCollectionTypeIND());
				stmt.setString(3, sResultinfo.getResultName());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsResultInforamtionInserted = false;
			ex.printStackTrace();
		}
		return IsResultInforamtionInserted;

	}

	public static boolean LoadEventDefinitions(Connection Connection,
			java.util.List<LU_Event_Definitions> EventDefinitions) {
		boolean IsEventDefinitionsInserted = true;
		try {

			for (LU_Event_Definitions sEventDefinitions : EventDefinitions) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_EventDefinitions (EVENT_ID, EVENT_NAME) values (?, ?)");
				stmt.setInt(1, Integer.parseInt(sEventDefinitions.getEventID()));
				stmt.setString(2, sEventDefinitions.getEventName());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsEventDefinitionsInserted = false;
			ex.printStackTrace();
		}
		return IsEventDefinitionsInserted;

	}

	public static boolean LoadEventTransitionDefinitions(
			Connection Connection,
			java.util.List<LU_Event_Transition_Definitions> EventTransitionDefinitions) {
		boolean IsEventTransitionDefinitionsInserted = true;
		try {

			for (LU_Event_Transition_Definitions sEventTransitionDefinitions : EventTransitionDefinitions) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_Event_Transition_Definitions (EVENT_TRANSITION_ID, EVENT_TRANSITION_NAME) values (?, ?)");
				stmt.setInt(1, Integer.parseInt(sEventTransitionDefinitions
						.getEventTransitionID()));
				stmt.setString(2,
						sEventTransitionDefinitions.getEventTransitionName());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsEventTransitionDefinitionsInserted = false;
			ex.printStackTrace();
		}
		return IsEventTransitionDefinitionsInserted;

	}

	public static boolean LoadMetricDefinitions(Connection Connection,
			java.util.List<LU_Metric_Definitions> MetricDefinitions) {
		boolean IsMetricDefinitionsInserted = true;
		try {

			for (LU_Metric_Definitions sMetricDefinitions : MetricDefinitions) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_Metric_Definitions (METRIC_ID, METRIC_NAME) values (?, ?)");
				stmt.setInt(1,
						Integer.parseInt(sMetricDefinitions.getMetricID()));
				stmt.setString(2, sMetricDefinitions.getMetricName());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsMetricDefinitionsInserted = false;
			ex.printStackTrace();
		}
		return IsMetricDefinitionsInserted;

	}

	public static boolean LoadMetricValidRanges(Connection Connection,
			java.util.List<LU_MetricValidRanges> MetricValidRanges) {
		boolean IsMetricValidRangesInserted = true;
		try {

			for (LU_MetricValidRanges sMetricValidRanges : MetricValidRanges) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_Metric_Valid_Ranges (METRIC_ID, MIN_VALUE,MAX_VALUE) values (?, ?,?)");
				stmt.setString(1, sMetricValidRanges.getMetricID());
				stmt.setString(2, sMetricValidRanges.getMinValue());
				stmt.setString(3, sMetricValidRanges.getMaxValue());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsMetricValidRangesInserted = false;
			ex.printStackTrace();
		}
		return IsMetricValidRangesInserted;

	}

	public static boolean LoadAggrigationTypes(Connection Connection,
			java.util.List<LU_Aggrigation_Types> AggrigationTypes) {
		boolean IsAggrigationTypesInserted = true;
		try {

			for (LU_Aggrigation_Types saggrigationtype : AggrigationTypes) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_AGREGATION_TYPE (AGGREAGATION_TYPE, AGGREGATION_ID) values (?, ?)");
				stmt.setString(1, saggrigationtype.getAggregationType());
				stmt.setString(2, saggrigationtype.getAggrigationID());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsAggrigationTypesInserted = false;
			ex.printStackTrace();
		}
		return IsAggrigationTypesInserted;

	}

	public static boolean LoadEntityTypes(Connection Connection,
			java.util.List<LU_Entity_Type> EntityTypes) {
		boolean IssEntityTypesInserted = true;
		try {

			for (LU_Entity_Type sEntityTypes : EntityTypes) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_ENTITY_TYPE (ENTITY_NAME, EXAMPLE,POSITION) values (?, ?,?)");
				stmt.setString(1, sEntityTypes.getEntityName());
				stmt.setString(2, sEntityTypes.getEntityExample());
				stmt.setString(3, sEntityTypes.getEntityPosition());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IssEntityTypesInserted = false;
			ex.printStackTrace();
		}
		return IssEntityTypesInserted;

	}

	public static boolean LoadEventPhases(Connection Connection,
			java.util.List<LU_Event_Phase> EventPhases) {
		boolean IsEventTypesInserted = true;
		try {

			for (LU_Event_Phase seventphases : EventPhases) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_EVENT_PHASE (EVENT_PHASE, EVENT_PHASE_ID) values (?, ?)");
				stmt.setString(1, seventphases.getEventPhase());
				stmt.setString(2, seventphases.getEventPhaseId());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsEventTypesInserted = false;
			ex.printStackTrace();
		}
		return IsEventTypesInserted;

	}

	public static boolean LoadEventStates(Connection Connection,
			java.util.List<LU_Event_State> EventStates) {
		boolean IsEventStateInserted = true;
		try {

			for (LU_Event_State seventstates : EventStates) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_EVENT_STATE (EVENT_STATE, EVENT_STATE_ID) values (?, ?)");
				stmt.setString(1, seventstates.getEventState());
				stmt.setString(2, seventstates.getEventStateId());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsEventStateInserted = false;
			ex.printStackTrace();
		}
		return IsEventStateInserted;

	}

	public static boolean LoadNewMetricDefinitions(Connection Connection,
			java.util.List<LU_MetricDefinitions> MetricDefinitions) {
		boolean IsNewMetricDefinitionsInserted = true;
		try {

			for (LU_MetricDefinitions smetricdefinitions : MetricDefinitions) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_EVENT_STATE (EVENT_STATE, EVENT_STATE_ID) values (?, ?)");
				stmt.setString(1, smetricdefinitions.getMetricDefinition());
				stmt.setString(2, smetricdefinitions.getMetricDefinitionId());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsNewMetricDefinitionsInserted = false;
			ex.printStackTrace();
		}
		return IsNewMetricDefinitionsInserted;

	}

	public static boolean LoadMetricNames(Connection Connection,
			java.util.List<LU_Metric_Names> MetricNames) {
		boolean IsMetricNamesInserted = true;
		try {

			for (LU_Metric_Names smetricnames : MetricNames) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_METRIC_NAME (METRIC_NAME, METRIC_ID) values (?, ?)");
				stmt.setString(1, smetricnames.getMetricName());
				stmt.setString(2, smetricnames.getMetricId());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsMetricNamesInserted = false;
			ex.printStackTrace();
		}
		return IsMetricNamesInserted;
	}

	public static boolean LoadTaskResults(Connection Connection,
			java.util.List<LU_Task_Results> TaskResults) {
		boolean IsTaskResultsInserted = true;
		try {

			for (LU_Task_Results staskresults : TaskResults) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_TASK_RESULTS (TASK_RESULTS_CLASSIFICATION, TASK_RESULTS_CLASSIFICATION_ID,TASK_RESULTS,TASK_RESULTS_ID,TASK_RESULTS_CODE) values (?, ?,?,?,?)");
				stmt.setString(1, staskresults.getTASK_RESULTS_CLASSIFICATION());
				stmt.setString(2,
						staskresults.getTASK_RESULTS_CLASSIFICATION_ID());
				stmt.setString(3, staskresults.getTASK_RESULTS());
				stmt.setString(4, staskresults.getTASK_RESULTS_ID());
				stmt.setString(5, staskresults.getTASK_RESULTS_CODE());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsTaskResultsInserted = false;
			ex.printStackTrace();
		}
		return IsTaskResultsInserted;
	}

	public static boolean LoadTaskTypes(Connection Connection,
			java.util.List<LU_Task_Types> TaskTypes) {
		boolean IsTaskTypesInserted = true;
		try {

			for (LU_Task_Types staskresults : TaskTypes) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_TASK_TYPE (TASK_TYPE, TASK_TYPE_ID) values (?, ?)");
				stmt.setString(1, staskresults.getTaskType());
				stmt.setString(2, staskresults.getTaskId());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsTaskTypesInserted = false;
			ex.printStackTrace();
		}
		return IsTaskTypesInserted;
	}

	public static boolean LoadTechnologyModes(Connection Connection,
			java.util.List<LU_Technology_Mode> TechnologyModes) {
		boolean IsTechnologyModesInserted = true;
		try {

			for (LU_Technology_Mode stechnologymodes : TechnologyModes) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_TECHNOLOGY_MODE (TECHNOLOGY_MODE, TECHNOLOGY_MODE_ID) values (?, ?)");
				stmt.setString(1, stechnologymodes.getTechnologyMode());
				stmt.setString(2, stechnologymodes.getTechnologyModeId());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsTechnologyModesInserted = false;
			ex.printStackTrace();
		}
		return IsTechnologyModesInserted;
	}

	public static boolean LoadTextFileNames(Connection Connection,
			java.util.List<LU_Textfile_Name> TextFileNames) {
		boolean IsTextFileNamesInserted = true;
		try {

			for (LU_Textfile_Name stechfilenames : TextFileNames) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_TEXTFILE_NAME (TEXTFILE_NAME, TEXTFILE_ID) values (?, ?)");
				stmt.setString(1, stechfilenames.getTextFileName());
				stmt.setString(2, stechfilenames.getTextFileId());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsTextFileNamesInserted = false;
			ex.printStackTrace();
		}
		return IsTextFileNamesInserted;
	}

	public static boolean LoadCollectionTypes(Connection Connection,
			java.util.List<LU_Collection_Type> CollectiontypeInfo) {
		boolean IsCollectionTypeInserted = true;
		try {

			for (LU_Collection_Type Collectioninfo : CollectiontypeInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_COLLCETION_PLATFORM (COLLECTION_TYPE, COLLECTION_PLATFORM) values (?, ?)");
				stmt.setString(1, Collectioninfo.getCOLLECTION_TYPE_IND());
				stmt.setString(2, Collectioninfo.getCOLLECTION_PLATFORM());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsCollectionTypeInserted = false;
			ex.printStackTrace();
		}
		return IsCollectionTypeInserted;

	}

	public static boolean LoadEventMessageTables(Connection Connection,
			java.util.List<LU_Event_Tables> EventMessageTablesInfo) {
		boolean IsEventMessageTablesInserted = true;
		try {

			for (LU_Event_Tables MessageTablesinfo : EventMessageTablesInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_EVENT_MESSAGE_TABLES (COLLECTION_TYPE,COLUMN_NAME, TEXTFILE_NAME) values (?, ?,?)");
				stmt.setString(1, MessageTablesinfo.getCOLLECTION_TYPE());
				stmt.setString(2, MessageTablesinfo.getCOLUMN_NAME());
				stmt.setString(3, MessageTablesinfo.getTEXTFILE_NAME());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsEventMessageTablesInserted = false;
			ex.printStackTrace();
		}
		return IsEventMessageTablesInserted;

	}

	public static boolean LoadEventFieldTables(Connection Connection,
			java.util.List<LU_Event_Tables> EventMessageTablesInfo) {
		boolean IsEventFieldTablesInserted = true;
		try {

			for (LU_Event_Tables MessageTablesinfo : EventMessageTablesInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_EVENT_FIELD_TABLES (COLLECTION_TYPE,COLUMN_NAME, TEXTFILE_NAME) values (?, ?,?)");
				stmt.setString(1, MessageTablesinfo.getCOLLECTION_TYPE());
				stmt.setString(2, MessageTablesinfo.getCOLUMN_NAME());
				stmt.setString(3, MessageTablesinfo.getTEXTFILE_NAME());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsEventFieldTablesInserted = false;
			ex.printStackTrace();
		}
		return IsEventFieldTablesInserted;

	}

	public static boolean LoadEventPhaseCorrelations(
			Connection Connection,
			java.util.List<LU_Event_Phase_Correlations> EventPhaseCorrelationsInfo) {
		boolean IsPhaseEventCorrelationsInserted = true;
		try {

			for (LU_Event_Phase_Correlations EventPhaseCorrelationinfo : EventPhaseCorrelationsInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_PHASE_EVENT_CORRELATION (COLLECTION_TYPE,PHASE_ID, START_EVENT_ID,END_EVENT_ID) values (?, ?,?,?)");
				stmt.setString(1,
						EventPhaseCorrelationinfo.getCOLLECTION_TYPE());
				stmt.setString(2, EventPhaseCorrelationinfo.getPHASE_ID());
				stmt.setString(3, EventPhaseCorrelationinfo.getSTART_EVENT_ID());
				stmt.setString(4, EventPhaseCorrelationinfo.getEND_EVENT_ID());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsPhaseEventCorrelationsInserted = false;
			ex.printStackTrace();
		}
		return IsPhaseEventCorrelationsInserted;

	}

	public static boolean LoadEventInfos(Connection Connection,
			java.util.List<LU_Event_Info> EventInfo) {
		boolean IsPhaseEventCorrelationsInserted = true;
		try {

			for (LU_Event_Info Event_Info : EventInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_EVENT_INFO (COLLECTION_TYPE,EVENT_NAME, MODE,TEXTFILE_NAME,EVENT_ID) values (?, ?,?,?,?)");
				stmt.setString(1, Event_Info.getCollection_type());
				stmt.setString(2, Event_Info.getEVENT_NAME());
				stmt.setString(3, Event_Info.getMODE());
				stmt.setString(4, Event_Info.getTEXTFILE_NAME());
				stmt.setString(5, Event_Info.getEVENT_ID());
				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsPhaseEventCorrelationsInserted = false;
			ex.printStackTrace();
		}
		return IsPhaseEventCorrelationsInserted;

	}

	public static boolean LoadDatetimeFields(Connection Connection,
			java.util.List<LU_DateTime_Fields> DatetimefieldsInfo) {
		boolean IsPhaseEventCorrelationsInserted = true;
		try {

			for (LU_DateTime_Fields Datetimefields_Info : DatetimefieldsInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_DATETIME_FIELDS (TEXTFILE_NAME,COLUMN_NAME) values (?, ?)");
				stmt.setString(1, Datetimefields_Info.getTextfile_name());
				stmt.setString(2, Datetimefields_Info.getColumn_name());

				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsPhaseEventCorrelationsInserted = false;
			ex.printStackTrace();
		}
		return IsPhaseEventCorrelationsInserted;

	}

	public static boolean LoadTFNC(Connection Connection,
			java.util.List<LU_TextFile_Collection_Type> TFNCInfo) {
		boolean IsPhaseEventCorrelationsInserted = true;
		try {

			for (LU_TextFile_Collection_Type TFNC_Info : TFNCInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.LU_TEXTFILE_COLLECTION_TYPE (TEXTFILE_NAME,COLLECTION_TYPE) values (?, ?)");
				stmt.setString(1, TFNC_Info.getTextFileName());
				stmt.setString(2, TFNC_Info.getCollectionType());

				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsPhaseEventCorrelationsInserted = false;
			ex.printStackTrace();
		}
		return IsPhaseEventCorrelationsInserted;

	}

	public static boolean LoadTestCaseStatus(Connection Connection,
			java.util.List<MD_TestCase_Status> MDTCSInfo) {
		boolean IsPhaseEventCorrelationsInserted = true;
		try {

			for (MD_TestCase_Status MDTCS_Info : MDTCSInfo) {

				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.MD_TESTCASE_STATUS (STATUS_ID,STATUS) values (?, ?)");
				stmt.setString(1, MDTCS_Info.getStatusId());
				stmt.setString(2, MDTCS_Info.getStatusComments());

				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsPhaseEventCorrelationsInserted = false;
			ex.printStackTrace();
		}
		return IsPhaseEventCorrelationsInserted;

	}

	public static boolean LoadTestCases(Connection Connection,
			java.util.List<TestCases> TestCases) {
		boolean IsTestCaseInserted = true;
		try {

			for (TestCases sTestCases : TestCases) {
				PreparedStatement stmt = Connection
						.prepareStatement("INSERT INTO dbo.TESTCASES (TESTCASE_ID, DRIVE_ID,TESTCASE_NAME,AGGREATION_TYPE_ID,TASK_TYPE_ID,TASK_RESULTS_ID,EVENT_PHASE_ID,EVENT_STATE_ID,TECHNOLOGY_MODE_ID,METRIC_ID,TEXTFILE_ID,METRIC_CODE) values (?, ?,?,?,?,?,?,?,?,?,?,?)");
				String MetricCode = sTestCases.getAggregationType()
						+ sTestCases.getTaskType()
						+ sTestCases.getTaskresults()
						+ sTestCases.getEventPhase()
						+ sTestCases.getEventState()
						+ sTestCases.getTechnologyMode()
						+ sTestCases.getMetricId() + sTestCases.getTextfileId();

				stmt.setString(1, sTestCases.getTCID());
				stmt.setString(2, sTestCases.getDRIVE_ID());
				stmt.setString(3, sTestCases.getTCName());
				stmt.setString(4, sTestCases.getAggregationType());
				stmt.setString(5, sTestCases.getTaskType());
				stmt.setString(6, sTestCases.getTaskresults());
				stmt.setString(7, sTestCases.getEventPhase());
				stmt.setString(8, sTestCases.getEventState());
				stmt.setString(9, sTestCases.getTechnologyMode());
				stmt.setString(10, sTestCases.getMetricId());
				stmt.setString(11, sTestCases.getTextfileId());
				stmt.setString(12, MetricCode);

				stmt.executeUpdate();
				Connection.commit();
			}

		} catch (Exception ex) {
			IsTestCaseInserted = false;
			ex.printStackTrace();
		}
		return IsTestCaseInserted;
	}
}
