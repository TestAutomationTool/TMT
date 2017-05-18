/**
 * 
 */
package com.nielsen.engineering.wse.DriveDataLoading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import com.nielsen.engineering.wse.ToStringClasses.StagingTablesList;
import com.nielsen.engineering.wse.ToStringClasses.TableNameAndClassMapping;
import com.nielsen.engineering.wse.ToStringClasses.TestCases;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class LoadConfigurationData {

	public static List<TestCasesVariables> ReadTestCases(String TestCasesXml) {
		List<TestCasesVariables> TestCasesList = new ArrayList<TestCasesVariables>();

		try {
			File fXmlFile = new File(TestCasesXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("TCID");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					TestCasesVariables TestCaseRules = new TestCasesVariables();
					Element eElement = (Element) nNode;
					TestCaseRules.setTestCaseId(eElement.getAttribute("ID"));
					TestCaseRules.setTestcaseName(eElement
							.getElementsByTagName("TCName").item(0)
							.getTextContent());
					TestCaseRules.setDriveID(eElement
							.getElementsByTagName("DRIVE_ID").item(0)
							.getTextContent());
					TestCaseRules.setAggregationType(eElement
							.getElementsByTagName("AggregationType").item(0)
							.getTextContent());
					TestCaseRules.setTaskType(eElement
							.getElementsByTagName("TaskType").item(0)
							.getTextContent());
					TestCaseRules.setTaskresults(eElement
							.getElementsByTagName("Taskresults").item(0)
							.getTextContent());
					TestCaseRules.setEventPhase(eElement
							.getElementsByTagName("EventPhase").item(0)
							.getTextContent());
					TestCaseRules.setEventState(eElement
							.getElementsByTagName("EventState").item(0)
							.getTextContent());
					TestCaseRules.setTechnologyMode(eElement
							.getElementsByTagName("TechnologyMode").item(0)
							.getTextContent());
					TestCaseRules.setMetricName(eElement
							.getElementsByTagName("MetricId").item(0)
							.getTextContent());
					TestCaseRules.setTextfileName(eElement
							.getElementsByTagName("TextfileId").item(0)
							.getTextContent());
					TestCasesList.add(TestCaseRules);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TestCasesList;
	}

	/*
	 * Read Staging Tables List
	 */
	public static List<StagingTablesList> ReadStagingTables(
			String StagingTableXml) {
		List<StagingTablesList> StagingTablesList = new ArrayList<StagingTablesList>();
		try {
			File fXmlFile = new File(StagingTableXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Table");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					StagingTablesList StagingTables = new StagingTablesList();
					Element eElement = (Element) nNode;
					StagingTables.setTableId(eElement.getAttribute("ID"));
					StagingTables.setTableName(eElement
							.getElementsByTagName("TableName").item(0)
							.getTextContent());
					StagingTablesList.add(StagingTables);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return StagingTablesList;
	}

	/*
	 * Read Event Definitions
	 */
	public static List<LU_Event_Definitions> EventDefinitions(
			String EventDefinitionXml) {
		List<LU_Event_Definitions> EventDefinitionsList = new ArrayList<LU_Event_Definitions>();
		try {
			File fXmlFile = new File(EventDefinitionXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Event");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_Definitions EventDefinitions = new LU_Event_Definitions();
					Element eElement = (Element) nNode;
					EventDefinitions.setEventID(eElement.getAttribute("ID"));
					EventDefinitions.setEventName(eElement
							.getElementsByTagName("EventName").item(0)
							.getTextContent());
					EventDefinitionsList.add(EventDefinitions);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EventDefinitionsList;
	}

	/*
	 * Read Event Transition Definitions
	 */
	public static List<LU_Event_Transition_Definitions> EventTransitionDefinitions(
			String EvenTransitionDefinitionXml) {
		List<LU_Event_Transition_Definitions> EventTransitionDefinitionsList = new ArrayList<LU_Event_Transition_Definitions>();
		try {
			File fXmlFile = new File(EvenTransitionDefinitionXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("EventTransition");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_Transition_Definitions EventTransitionDefinitions = new LU_Event_Transition_Definitions();
					Element eElement = (Element) nNode;
					EventTransitionDefinitions.setEventTransitionID(eElement
							.getAttribute("ID"));
					EventTransitionDefinitions.setEventTransitionName(eElement
							.getElementsByTagName("EventTransitionName")
							.item(0).getTextContent());
					EventTransitionDefinitionsList
							.add(EventTransitionDefinitions);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EventTransitionDefinitionsList;
	}

	/*
	 * Read Metric Definitions
	 */
	public static List<LU_Metric_Definitions> MetricDefinitions(
			String MetricDefinitionsXml) {
		List<LU_Metric_Definitions> MetricDefinitionsList = new ArrayList<LU_Metric_Definitions>();
		try {
			File fXmlFile = new File(MetricDefinitionsXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Metric");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Metric_Definitions MetricDefinitions = new LU_Metric_Definitions();
					Element eElement = (Element) nNode;
					MetricDefinitions.setMetricID(eElement.getAttribute("ID"));
					MetricDefinitions.setMetricName(eElement
							.getElementsByTagName("MetricName").item(0)
							.getTextContent());
					MetricDefinitionsList.add(MetricDefinitions);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MetricDefinitionsList;
	}

	/*
	 * Read Metric Definitions
	 */
	public static List<LU_Result_Information> ResultInformation(
			String ResultInformationXml) {
		List<LU_Result_Information> ResultInformationList = new ArrayList<LU_Result_Information>();
		try {
			File fXmlFile = new File(ResultInformationXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Result");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Result_Information ResultInformation = new LU_Result_Information();
					Element eElement = (Element) nNode;
					ResultInformation.setResultID(eElement.getAttribute("ID"));
					ResultInformation.setResultName(eElement
							.getElementsByTagName("ResultName").item(0)
							.getTextContent());
					ResultInformation.setCollectionTypeIND(eElement
							.getElementsByTagName("CollectionTypeIND").item(0)
							.getTextContent());

					ResultInformationList.add(ResultInformation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResultInformationList;
	}

	/*
	 * Read TableName and Class Mapping Definitions
	 */
	public static List<TableNameAndClassMapping> TableNameAndClassMapping(
			String ClassMappingxml) {
		List<TableNameAndClassMapping> TableNameAndClassList = new ArrayList<TableNameAndClassMapping>();
		try {
			File fXmlFile = new File(ClassMappingxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Class");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					TableNameAndClassMapping TableNamesAndClass = new TableNameAndClassMapping();
					Element eElement = (Element) nNode;
					TableNamesAndClass.setClassID(eElement.getAttribute("ID"));
					TableNamesAndClass.setClassName(eElement
							.getElementsByTagName("ClassName").item(0)
							.getTextContent());
					TableNamesAndClass.setInsertTableName(eElement
							.getElementsByTagName("InsertTableName").item(0)
							.getTextContent());
					TableNamesAndClass.setParentTableName(eElement
							.getElementsByTagName("ParentTableName").item(0)
							.getTextContent());

					TableNameAndClassList.add(TableNamesAndClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TableNameAndClassList;
	}

	/*
	 * Read Market Carrier Information
	 */
	public static List<MarketCarrierInformation> MarketCarrierInfo(
			String ClassMappingxml) {
		List<MarketCarrierInformation> MarketCarrierInfoList = new ArrayList<MarketCarrierInformation>();
		try {
			File fXmlFile = new File(ClassMappingxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Market");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					MarketCarrierInformation MarketCarrierInfo = new MarketCarrierInformation();
					Element eElement = (Element) nNode;

					MarketCarrierInfo.setPORT(eElement
							.getElementsByTagName("PORT").item(0)
							.getTextContent());
					MarketCarrierInfo.setDEVICE_TECHNOLOGY_NAME(eElement
							.getElementsByTagName("DEVICE_TECHNOLOGY_NAME")
							.item(0).getTextContent());

					MarketCarrierInfo.setCARRIER_NAME(eElement
							.getElementsByTagName("CARRIER_NAME").item(0)
							.getTextContent());

					MarketCarrierInfo.setSUPPORTED_TECHNOLOGY(eElement
							.getElementsByTagName("SUPPORTED_TECHNOLOGY")
							.item(0).getTextContent());

					MarketCarrierInfoList.add(MarketCarrierInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MarketCarrierInfoList;
	}

	/*
	 * Read DriveID Carrier Information
	 */
	public static List<DriveIdInformation> DriveIDInfo(String ClassMappingxml) {
		List<DriveIdInformation> DriveIDInfoList = new ArrayList<DriveIdInformation>();
		try {
			File fXmlFile = new File(ClassMappingxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Drive");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					DriveIdInformation DriveIDInfo = new DriveIdInformation();
					Element eElement = (Element) nNode;

					DriveIDInfo.setDRIVEID(eElement
							.getElementsByTagName("DRIVEID").item(0)
							.getTextContent());
					DriveIDInfo.setCOLLECTION_TYPE(eElement
							.getElementsByTagName("COLLECTION_TYPE").item(0)
							.getTextContent());
					DriveIDInfoList.add(DriveIDInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return DriveIDInfoList;
	}

	public static List<LU_MetricValidRanges> MetricValiedRanges(
			String MetricValidRangexml) {
		List<LU_MetricValidRanges> MetricValidInfo = new ArrayList<LU_MetricValidRanges>();
		try {
			File fXmlFile = new File(MetricValidRangexml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Metric");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_MetricValidRanges metricvalidinfo = new LU_MetricValidRanges();
					Element eElement = (Element) nNode;

					metricvalidinfo.setMetricID(eElement.getAttribute("ID"));
					metricvalidinfo.setMinValue(eElement
							.getElementsByTagName("MinValue").item(0)
							.getTextContent());
					metricvalidinfo.setMaxValue(eElement
							.getElementsByTagName("MaxValue").item(0)
							.getTextContent());
					MetricValidInfo.add(metricvalidinfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MetricValidInfo;
	}

	/*
	 * Look up Tables List 1) Aggrigation Types
	 */
	public static List<LU_Aggrigation_Types> AggrigationTypes(
			String AggrigationTypesxml) {
		List<LU_Aggrigation_Types> AggrigationTypesInfo = new ArrayList<LU_Aggrigation_Types>();
		try {
			File fXmlFile = new File(AggrigationTypesxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Aggregation");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Aggrigation_Types aggrigationtype = new LU_Aggrigation_Types();
					Element eElement = (Element) nNode;

					aggrigationtype.setAggregationType(eElement
							.getElementsByTagName("AggregationType").item(0)
							.getTextContent());
					aggrigationtype.setAggrigationID(eElement
							.getElementsByTagName("AggregationID").item(0)
							.getTextContent());
					AggrigationTypesInfo.add(aggrigationtype);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return AggrigationTypesInfo;
	}

	/*
	 * Entity Type list
	 */
	public static List<LU_Entity_Type> EntityTypes(String EntityTypesxml) {
		List<LU_Entity_Type> EntityTypesInfo = new ArrayList<LU_Entity_Type>();
		try {
			File fXmlFile = new File(EntityTypesxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Entity");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Entity_Type entirytype = new LU_Entity_Type();
					Element eElement = (Element) nNode;

					entirytype.setEntityName(eElement
							.getElementsByTagName("EntityName").item(0)
							.getTextContent());
					entirytype.setEntityExample(eElement
							.getElementsByTagName("Example").item(0)
							.getTextContent());
					entirytype.setEntityPosition(eElement
							.getElementsByTagName("Position").item(0)
							.getTextContent());
					EntityTypesInfo.add(entirytype);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EntityTypesInfo;
	}

	/*
	 * Event Phase list
	 */
	public static List<LU_Event_Phase> EventPhase(String EventPhasexml) {
		List<LU_Event_Phase> EventPhaseInfo = new ArrayList<LU_Event_Phase>();
		try {
			File fXmlFile = new File(EventPhasexml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("EventPhase");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_Phase eventphase = new LU_Event_Phase();
					Element eElement = (Element) nNode;

					eventphase.setEventPhase(eElement
							.getElementsByTagName("EventPhaseName").item(0)
							.getTextContent());
					eventphase.setEventPhaseId(eElement
							.getElementsByTagName("EventPhaseId").item(0)
							.getTextContent());

					EventPhaseInfo.add(eventphase);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EventPhaseInfo;
	}

	/*
	 * Event Sate list
	 */
	public static List<LU_Event_State> EventSates(String EventStatexml) {
		List<LU_Event_State> EventStateInfo = new ArrayList<LU_Event_State>();
		try {
			File fXmlFile = new File(EventStatexml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("EventState");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_State eventstate = new LU_Event_State();
					Element eElement = (Element) nNode;

					eventstate.setEventState(eElement
							.getElementsByTagName("EventStateName").item(0)
							.getTextContent());
					eventstate.setEventStateId(eElement
							.getElementsByTagName("EventStateId").item(0)
							.getTextContent());

					EventStateInfo.add(eventstate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EventStateInfo;
	}

	/*
	 * Metric Definitions list
	 */
	public static List<LU_MetricDefinitions> lMetricDefinitions(
			String MetricDefinitionxml) {
		List<LU_MetricDefinitions> MetricDefinitionInfo = new ArrayList<LU_MetricDefinitions>();
		try {
			File fXmlFile = new File(MetricDefinitionxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("MetricDefinition");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_MetricDefinitions metricdefinitions = new LU_MetricDefinitions();
					Element eElement = (Element) nNode;

					metricdefinitions.setMetricDefinition(eElement
							.getElementsByTagName("MetricDefinitionName")
							.item(0).getTextContent());
					metricdefinitions.setMetricDefinitionId(eElement
							.getElementsByTagName("MetricDefinitionId").item(0)
							.getTextContent());

					MetricDefinitionInfo.add(metricdefinitions);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MetricDefinitionInfo;
	}

	/*
	 * Metric Names list
	 */
	public static List<LU_Metric_Names> MetricNames(String MetricNamesxml) {
		List<LU_Metric_Names> MetricNameInfo = new ArrayList<LU_Metric_Names>();
		try {
			File fXmlFile = new File(MetricNamesxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Metric");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Metric_Names metricnames = new LU_Metric_Names();
					Element eElement = (Element) nNode;
					metricnames.setMetricName(eElement
							.getElementsByTagName("MetricName").item(0)
							.getTextContent());
					metricnames.setMetricId(eElement
							.getElementsByTagName("MetricId").item(0)
							.getTextContent());
					MetricNameInfo.add(metricnames);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MetricNameInfo;
	}

	/*
	 * Task Results list
	 */
	public static List<LU_Task_Results> TaskResults(String Taskresultxml) {
		List<LU_Task_Results> TaskResultInfo = new ArrayList<LU_Task_Results>();
		try {
			File fXmlFile = new File(Taskresultxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("TASKRESULT");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Task_Results taskresult = new LU_Task_Results();
					Element eElement = (Element) nNode;
					taskresult
							.setTASK_RESULTS_CLASSIFICATION(eElement
									.getElementsByTagName("TASK_RESULTS_CLASSIFICATION")
									.item(0).getTextContent());
					taskresult.setTASK_RESULTS_CLASSIFICATION_ID(eElement
							.getElementsByTagName("TASK_RESULTS_CLASSIFICATION_ID").item(0)
							.getTextContent());
					taskresult.setTASK_RESULTS(eElement
							.getElementsByTagName("TASK_RESULTS").item(0)
							.getTextContent());
					taskresult.setTASK_RESULTS_ID(eElement
							.getElementsByTagName("TASK_RESULTS_ID").item(0)
							.getTextContent());
					taskresult.setTASK_RESULTS_CODE(eElement
							.getElementsByTagName("TASK_RESULTS_CODE").item(0)
							.getTextContent());
					TaskResultInfo.add(taskresult);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaskResultInfo;
	}

	/*
	 * Task types list
	 */
	public static List<LU_Task_Types> TaskTypes(String TaskTypesxml) {
		List<LU_Task_Types> TaskTypesInfo = new ArrayList<LU_Task_Types>();
		try {
			File fXmlFile = new File(TaskTypesxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("TaskType");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Task_Types taskresult = new LU_Task_Types();
					Element eElement = (Element) nNode;
					taskresult.setTaskType(eElement
							.getElementsByTagName("TaskTypeName").item(0)
							.getTextContent());
					taskresult.setTaskId(eElement
							.getElementsByTagName("TaskId").item(0)
							.getTextContent());
					TaskTypesInfo.add(taskresult);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaskTypesInfo;
	}

	/*
	 * Technology Modes list
	 */
	public static List<LU_Technology_Mode> TechnologyMode(
			String TechnologyModexml) {
		List<LU_Technology_Mode> TechnologyModeInfo = new ArrayList<LU_Technology_Mode>();
		try {
			File fXmlFile = new File(TechnologyModexml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("TechnologyMode");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Technology_Mode technologymode = new LU_Technology_Mode();
					Element eElement = (Element) nNode;
					technologymode.setTechnologyMode(eElement
							.getElementsByTagName("TechnologyModeName").item(0)
							.getTextContent());
					technologymode.setTechnologyModeId(eElement
							.getElementsByTagName("TechnologyModeId").item(0)
							.getTextContent());
					TechnologyModeInfo.add(technologymode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TechnologyModeInfo;
	}

	/*
	 * TextFile Modes list
	 */
	public static List<LU_Textfile_Name> TextFiles(String TextFilesxml) {
		List<LU_Textfile_Name> TextFilesInfo = new ArrayList<LU_Textfile_Name>();
		try {
			File fXmlFile = new File(TextFilesxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("TextFile");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Textfile_Name textfiles = new LU_Textfile_Name();
					Element eElement = (Element) nNode;
					textfiles.setTextFileName(eElement
							.getElementsByTagName("TextFileName").item(0)
							.getTextContent());
					textfiles.setTextFileId(eElement
							.getElementsByTagName("TextFileId").item(0)
							.getTextContent());
					TextFilesInfo.add(textfiles);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TextFilesInfo;
	}

	/*
	 * Textcases list
	 */
	public static List<TestCases> TestCases(String TestCasesXml) {
		List<TestCases> TestCaseInfo = new ArrayList<TestCases>();
		try {
			File fXmlFile = new File(TestCasesXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("TCID");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					TestCases textcases = new TestCases();
					Element eElement = (Element) nNode;
					textcases.setTCID(eElement.getAttribute("ID"));
					textcases.setTCName(eElement.getElementsByTagName("TCName")
							.item(0).getTextContent());

					textcases.setDRIVE_ID(eElement
							.getElementsByTagName("DRIVE_ID").item(0)
							.getTextContent());
					textcases.setAggregationType(eElement
							.getElementsByTagName("AggregationType").item(0)
							.getTextContent());
					textcases.setTaskType(eElement
							.getElementsByTagName("TaskType").item(0)
							.getTextContent());
					textcases.setTaskresults(eElement
							.getElementsByTagName("Taskresults").item(0)
							.getTextContent());
					textcases.setEventPhase(eElement
							.getElementsByTagName("EventPhase").item(0)
							.getTextContent());
					textcases.setEventState(eElement
							.getElementsByTagName("EventState").item(0)
							.getTextContent());
					textcases.setTechnologyMode(eElement
							.getElementsByTagName("TechnologyMode").item(0)
							.getTextContent());
					textcases.setMetricId(eElement
							.getElementsByTagName("MetricId").item(0)
							.getTextContent());
					textcases.setTextfileId(eElement
							.getElementsByTagName("TextfileId").item(0)
							.getTextContent());
					TestCaseInfo.add(textcases);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TestCaseInfo;
	}

	/*
	 * Collection types list
	 */
	public static List<LU_Collection_Type> CollectionTypes(
			String CollectionTypexml) {
		List<LU_Collection_Type> CollectiontypeInfo = new ArrayList<LU_Collection_Type>();
		try {
			File fXmlFile = new File(CollectionTypexml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("COLLECTIONTYPE");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Collection_Type Collectiontype = new LU_Collection_Type();
					Element eElement = (Element) nNode;
					Collectiontype.setCOLLECTION_TYPE_IND(eElement
							.getElementsByTagName("COLLECTION_TYPE_IND")
							.item(0).getTextContent());
					Collectiontype.setCOLLECTION_PLATFORM(eElement
							.getElementsByTagName("COLLECTION_PLATFORM")
							.item(0).getTextContent());
					CollectiontypeInfo.add(Collectiontype);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CollectiontypeInfo;
	}

	/*
	 * Event message tables list
	 */
	public static List<LU_Event_Tables> EventMessageTables(String EventTablesxml) {
		List<LU_Event_Tables> EventTablesInfo = new ArrayList<LU_Event_Tables>();
		try {
			File fXmlFile = new File(EventTablesxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("MESSAGETABLE");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_Tables EventTables = new LU_Event_Tables();
					Element eElement = (Element) nNode;
					EventTables.setCOLLECTION_TYPE(eElement
							.getElementsByTagName("COLLECTION_TYPE").item(0)
							.getTextContent());
					EventTables.setCOLUMN_NAME(eElement
							.getElementsByTagName("COLUMN_NAME").item(0)
							.getTextContent());
					EventTables.setTEXTFILE_NAME(eElement
							.getElementsByTagName("TEXTFILE_NAME").item(0)
							.getTextContent());
					EventTablesInfo.add(EventTables);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EventTablesInfo;
	}

	/*
	 * Event tables fields list
	 */
	public static List<LU_Event_Tables> EventFieldTables(
			String EventFieldTablesxml) {
		List<LU_Event_Tables> EventTablesInfo = new ArrayList<LU_Event_Tables>();
		try {
			File fXmlFile = new File(EventFieldTablesxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("FIELDTABLE");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_Tables EventTables = new LU_Event_Tables();
					Element eElement = (Element) nNode;
					EventTables.setCOLLECTION_TYPE(eElement
							.getElementsByTagName("COLLECTION_TYPE").item(0)
							.getTextContent());
					EventTables.setCOLUMN_NAME(eElement
							.getElementsByTagName("COLUMN_NAME").item(0)
							.getTextContent());
					EventTables.setTEXTFILE_NAME(eElement
							.getElementsByTagName("TEXTFILE_NAME").item(0)
							.getTextContent());
					EventTablesInfo.add(EventTables);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EventTablesInfo;
	}

	/*
	 * Phase Event Correlations list
	 */
	public static List<LU_Event_Phase_Correlations> PhaseEventCorrelations(
			String EventPhaseCorrelationsxml) {
		List<LU_Event_Phase_Correlations> PhaseEventCorrelationInfo = new ArrayList<LU_Event_Phase_Correlations>();
		try {
			File fXmlFile = new File(EventPhaseCorrelationsxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("EVENTCORRELATION");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_Phase_Correlations PhaseEventCorrelations = new LU_Event_Phase_Correlations();
					Element eElement = (Element) nNode;
					PhaseEventCorrelations.setCOLLECTION_TYPE(eElement
							.getElementsByTagName("COLLECTION_TYPE").item(0)
							.getTextContent());
					PhaseEventCorrelations.setPHASE_ID(eElement
							.getElementsByTagName("PHASE_ID").item(0)
							.getTextContent());
					PhaseEventCorrelations.setSTART_EVENT_ID(eElement
							.getElementsByTagName("START_EVENT_ID").item(0)
							.getTextContent());
					PhaseEventCorrelations.setEND_EVENT_ID(eElement
							.getElementsByTagName("END_EVENT_ID").item(0)
							.getTextContent());
					PhaseEventCorrelationInfo.add(PhaseEventCorrelations);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PhaseEventCorrelationInfo;
	}

	/*
	 * Event info list
	 */
	public static List<LU_Event_Info> EventInfo(String EventInfoxml) {
		List<LU_Event_Info> EventInfoInfo = new ArrayList<LU_Event_Info>();
		try {
			File fXmlFile = new File(EventInfoxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("EVENTINFO");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_Event_Info Eventin = new LU_Event_Info();
					Element eElement = (Element) nNode;
					Eventin.setCollection_type(eElement
							.getElementsByTagName("COLLECTION_TYPE").item(0)
							.getTextContent());
					Eventin.setEVENT_NAME(eElement
							.getElementsByTagName("EVENT_NAME").item(0)
							.getTextContent());
					Eventin.setMODE(eElement.getElementsByTagName("MODE")
							.item(0).getTextContent());
					Eventin.setTEXTFILE_NAME(eElement
							.getElementsByTagName("TEXTFILE_NAME").item(0)
							.getTextContent());
					Eventin.setEVENT_ID(eElement
							.getElementsByTagName("EVENT_ID").item(0)
							.getTextContent());
					EventInfoInfo.add(Eventin);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EventInfoInfo;
	}

	/*
	 * DATE TIME FIELDS list
	 */
	public static List<LU_DateTime_Fields> DateTimeFields(String DateTimeInfoxml) {
		List<LU_DateTime_Fields> DatetimefieldsInfo = new ArrayList<LU_DateTime_Fields>();
		try {
			File fXmlFile = new File(DateTimeInfoxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("DATETIMECOLUMN");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_DateTime_Fields datetimeFileds = new LU_DateTime_Fields();
					Element eElement = (Element) nNode;
					datetimeFileds.setColumn_name(eElement
							.getElementsByTagName("COLUMN_NAME").item(0)
							.getTextContent());
					datetimeFileds.setTextfile_name(eElement
							.getElementsByTagName("TEXTFILE_NAME").item(0)
							.getTextContent());					 
					DatetimefieldsInfo.add(datetimeFileds);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return DatetimefieldsInfo;
	}
	
	/*
	 * TextFile Name and Collection types list
	 */
	public static List<LU_TextFile_Collection_Type> TextFileName_CollectionType(String TFNCInfoxml) {
		List<LU_TextFile_Collection_Type> TFNCInfo = new ArrayList<LU_TextFile_Collection_Type>();
		try {
			File fXmlFile = new File(TFNCInfoxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("CollectionTypes");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					LU_TextFile_Collection_Type TFNC = new LU_TextFile_Collection_Type();
					Element eElement = (Element) nNode;
					TFNC.setTextFileName(eElement
							.getElementsByTagName("TextFileName").item(0)
							.getTextContent());
					TFNC.setCollectionType(eElement
							.getElementsByTagName("CollectionType").item(0)
							.getTextContent());					 
					TFNCInfo.add(TFNC);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TFNCInfo;
	}
	
	/*
	 * TestCase Status list
	 */
	public static List<MD_TestCase_Status> TestCaseStatus(String TestCaseStatusInfoxml) {
		List<MD_TestCase_Status> MDTSInfo = new ArrayList<MD_TestCase_Status>();
		try {
			File fXmlFile = new File(TestCaseStatusInfoxml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Status");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					MD_TestCase_Status MDTS = new MD_TestCase_Status();
					Element eElement = (Element) nNode;
					MDTS.setStatusId(eElement
							.getElementsByTagName("StatusID").item(0)
							.getTextContent());
					MDTS.setStatusComments(eElement
							.getElementsByTagName("StatusComments").item(0)
							.getTextContent());					 
					MDTSInfo.add(MDTS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MDTSInfo;
	}
	public static StringBuffer sbHeaders(int HeaderLength, String Headers) {
		StringBuffer data = new StringBuffer();
		try {

			String[] header = new String[HeaderLength];
			for (int i = 0; i < HeaderLength; i++) {
				header[i] = Headers.split(",")[i];
				data.append(header[i] + ",");
			}
			data.append("\n");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// System.out.println(data);
		return data;
	}
}
