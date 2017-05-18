/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Metric_Names {
	private String MetricName;
	private String MetricId;
	@Override
	public String toString() {
		return "LU_Metric_Names [MetricName=" + MetricName + ", MetricId="
				+ MetricId + "]";
	}
	public String getMetricName() {
		return MetricName;
	}
	public void setMetricName(String metricName) {
		MetricName = metricName;
	}
	public String getMetricId() {
		return MetricId;
	}
	public void setMetricId(String metricId) {
		MetricId = metricId;
	}

}
