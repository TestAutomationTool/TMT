/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class DriveIdInformation {

	private String DRIVEID;
	public String getDRIVEID() {
		return DRIVEID;
	}
	public void setDRIVEID(String dRIVEID) {
		DRIVEID = dRIVEID;
	}
	public String getCOLLECTION_TYPE() {
		return COLLECTION_TYPE;
	}
	public void setCOLLECTION_TYPE(String cOLLECTION_TYPE) {
		COLLECTION_TYPE = cOLLECTION_TYPE;
	}
	private String COLLECTION_TYPE;
	@Override
	public String toString() {
		return "DriveIdInformation [DRIVEID=" + DRIVEID + ", COLLECTION_TYPE="
				+ COLLECTION_TYPE + "]";
	}
}
