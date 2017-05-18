/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Metric_Definitions {

	private String MetricID;
	private String MetricName;
	@Override
	public String toString() {
		return "LU_Metric_Definitions [MetricID=" + MetricID + ", MetricName="
				+ MetricName + "]";
	}
	public String getMetricID() {
		return MetricID;
	}
	public void setMetricID(String metricID) {
		MetricID = metricID;
	}
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String metricName) {
		MetricName = metricName;
	}
}
