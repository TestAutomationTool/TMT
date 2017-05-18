/**
 * 
 */
package com.nielsen.engineering.wse.DriveDataLoading;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class TestCasesVariables {

	private String TestCaseId;
	private String TestcaseName;
	private String DriveID;
	private String AggregationType;
	private String TaskType;
	private String Taskresults;
	private String EventPhase;
	private String EventState;
	private String TechnologyMode;
	private String MetricName;
	private String TextfileName;

	public String getTestCaseId() {
		return TestCaseId;
	}

	public void setTestCaseId(String testCaseId) {
		TestCaseId = testCaseId;
	}

	public String getTestcaseName() {
		return TestcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		TestcaseName = testcaseName;
	}

	public String getDriveID() {
		return DriveID;
	}

	public void setDriveID(String driveID) {
		DriveID = driveID;
	}

	public String getAggregationType() {
		return AggregationType;
	}

	public void setAggregationType(String aggregationType) {
		AggregationType = aggregationType;
	}

	public String getTaskType() {
		return TaskType;
	}

	public void setTaskType(String taskType) {
		TaskType = taskType;
	}

	public String getTaskresults() {
		return Taskresults;
	}

	public void setTaskresults(String taskresults) {
		Taskresults = taskresults;
	}

	public String getEventPhase() {
		return EventPhase;
	}

	public void setEventPhase(String eventPhase) {
		EventPhase = eventPhase;
	}

	public String getEventState() {
		return EventState;
	}

	public void setEventState(String eventState) {
		EventState = eventState;
	}

	public String getTechnologyMode() {
		return TechnologyMode;
	}

	public void setTechnologyMode(String technologyMode) {
		TechnologyMode = technologyMode;
	}

	public String getMetricName() {
		return MetricName;
	}

	public void setMetricName(String metricName) {
		MetricName = metricName;
	}

	public String getTextfileName() {
		return TextfileName;
	}

	public void setTextfileName(String textfileName) {
		TextfileName = textfileName;
	}

	@Override
	public String toString() {
		return "TestCasesVariables [TestCaseId=" + TestCaseId
				+ ", TestcaseName=" + TestcaseName + ", DriveID=" + DriveID
				+ ", AggregationType=" + AggregationType + ", TaskType="
				+ TaskType + ", Taskresults=" + Taskresults + ", EventPhase="
				+ EventPhase + ", EventState=" + EventState
				+ ", TechnologyMode=" + TechnologyMode + ", MetricName="
				+ MetricName + ", TextfileName=" + TextfileName + "]";
	}

}
