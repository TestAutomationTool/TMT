/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class TestCases {
	private String TCID;
	private String TCName;
	private String DRIVE_ID;
	private String AggregationType;
	private String TaskType;
	private String Taskresults;
	private String EventPhase;
	private String EventState;
	private String TechnologyMode;
	private String MetricId;
	private String TextfileId;
	public String getTCID() {
		return TCID;
	}
	public void setTCID(String tCID) {
		TCID = tCID;
	}
	public String getTCName() {
		return TCName;
	}
	public void setTCName(String tCName) {
		TCName = tCName;
	}
	public String getDRIVE_ID() {
		return DRIVE_ID;
	}
	public void setDRIVE_ID(String dRIVE_ID) {
		DRIVE_ID = dRIVE_ID;
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
	public String getMetricId() {
		return MetricId;
	}
	public void setMetricId(String metricId) {
		MetricId = metricId;
	}
	public String getTextfileId() {
		return TextfileId;
	}
	public void setTextfileId(String textfileId) {
		TextfileId = textfileId;
	}
	@Override
	public String toString() {
		return "TestCases [TCID=" + TCID + ", TCName=" + TCName + ", DRIVE_ID="
				+ DRIVE_ID + ", AggregationType=" + AggregationType
				+ ", TaskType=" + TaskType + ", Taskresults=" + Taskresults
				+ ", EventPhase=" + EventPhase + ", EventState=" + EventState
				+ ", TechnologyMode=" + TechnologyMode + ", MetricId="
				+ MetricId + ", TextfileId=" + TextfileId + "]";
	}

}
