/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class FunctionParameters {
	private String EventPhaseID;
	private String TextFilename;
	private String Metric;
	private String ConditionClause;
	private String ColumnName;

	public String getEventPhaseID() {
		return EventPhaseID;
	}

	public void setEventPhaseID(String eventPhaseID) {
		EventPhaseID = eventPhaseID;
	}

	public String getTextFilename() {
		return TextFilename;
	}

	public void setTextFilename(String textFilename) {
		TextFilename = textFilename;
	}

	public String getMetric() {
		return Metric;
	}

	public void setMetric(String metric) {
		Metric = metric;
	}

	public String getConditionClause() {
		return ConditionClause;
	}

	public void setConditionClause(String conditionClause) {
		ConditionClause = conditionClause;
	}

	public String getColumnName() {
		return ColumnName;
	}

	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}

	@Override
	public String toString() {
		return "FunctionParameters [EventPhaseID=" + EventPhaseID
				+ ", TextFilename=" + TextFilename + ", Metric=" + Metric
				+ ", ConditionClause=" + ConditionClause + ", ColumnName="
				+ ColumnName + "]";
	}

}
