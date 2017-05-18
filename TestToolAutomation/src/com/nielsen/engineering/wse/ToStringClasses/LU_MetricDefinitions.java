/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class LU_MetricDefinitions {
	private String MetricDefinition;
	private String MetricDefinitionId;
	public String getMetricDefinition() {
		return MetricDefinition;
	}
	public void setMetricDefinition(String metricDefinition) {
		MetricDefinition = metricDefinition;
	}
	public String getMetricDefinitionId() {
		return MetricDefinitionId;
	}
	public void setMetricDefinitionId(String metricDefinitionId) {
		MetricDefinitionId = metricDefinitionId;
	}
	@Override
	public String toString() {
		return "LU_MetricDefinitions [MetricDefinition=" + MetricDefinition
				+ ", MetricDefinitionId=" + MetricDefinitionId + "]";
	}
}
