/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Aggrigation_Types {

	private String AggregationType;
	private String AggrigationID;
	public String getAggregationType() {
		return AggregationType;
	}
	public void setAggregationType(String aggregationType) {
		AggregationType = aggregationType;
	}
	public String getAggrigationID() {
		return AggrigationID;
	}
	public void setAggrigationID(String aggrigationID) {
		AggrigationID = aggrigationID;
	}
	@Override
	public String toString() {
		return "LU_Aggrigation_Types [AggregationType=" + AggregationType
				+ ", AggrigationID=" + AggrigationID + "]";
	}
	 
}
