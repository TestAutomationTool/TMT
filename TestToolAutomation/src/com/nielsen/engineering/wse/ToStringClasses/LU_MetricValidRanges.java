/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_MetricValidRanges {

	
	private String MetricID;
	private String MinValue;
	private String MaxValue;
	public String getMetricID() {
		return MetricID;
	}
	public void setMetricID(String metricID) {
		MetricID = metricID;
	}
	@Override
	public String toString() {
		return "LU_MetricValidRanges [MetricID=" + MetricID + ", MinValue="
				+ MinValue + ", MaxValue=" + MaxValue + "]";
	}
	public String getMinValue() {
		return MinValue;
	}
	public void setMinValue(String minValue) {
		MinValue = minValue;
	}
	public String getMaxValue() {
		return MaxValue;
	}
	public void setMaxValue(String maxValue) {
		MaxValue = maxValue;
	}
}
