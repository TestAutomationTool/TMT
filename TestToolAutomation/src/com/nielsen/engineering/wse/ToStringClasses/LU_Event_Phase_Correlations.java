/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class LU_Event_Phase_Correlations {
	private String COLLECTION_TYPE;
	private String PHASE_ID;
	private String START_EVENT_ID;
	private String END_EVENT_ID;
	public String getCOLLECTION_TYPE() {
		return COLLECTION_TYPE;
	}
	public void setCOLLECTION_TYPE(String cOLLECTION_TYPE) {
		COLLECTION_TYPE = cOLLECTION_TYPE;
	}
	public String getPHASE_ID() {
		return PHASE_ID;
	}
	public void setPHASE_ID(String pHASE_ID) {
		PHASE_ID = pHASE_ID;
	}
	public String getSTART_EVENT_ID() {
		return START_EVENT_ID;
	}
	public void setSTART_EVENT_ID(String sTART_EVENT_ID) {
		START_EVENT_ID = sTART_EVENT_ID;
	}
	public String getEND_EVENT_ID() {
		return END_EVENT_ID;
	}
	public void setEND_EVENT_ID(String eND_EVENT_ID) {
		END_EVENT_ID = eND_EVENT_ID;
	}
	@Override
	public String toString() {
		return "LU_Event_Phase_Correlations [COLLECTION_TYPE="
				+ COLLECTION_TYPE + ", PHASE_ID=" + PHASE_ID
				+ ", START_EVENT_ID=" + START_EVENT_ID + ", END_EVENT_ID="
				+ END_EVENT_ID + "]";
	}
}
