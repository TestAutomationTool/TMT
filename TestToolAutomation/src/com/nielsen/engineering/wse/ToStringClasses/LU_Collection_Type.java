/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Collection_Type {

	private String COLLECTION_TYPE_IND;
	private String COLLECTION_PLATFORM;
	public String getCOLLECTION_TYPE_IND() {
		return COLLECTION_TYPE_IND;
	}
	public void setCOLLECTION_TYPE_IND(String cOLLECTION_TYPE_IND) {
		COLLECTION_TYPE_IND = cOLLECTION_TYPE_IND;
	}
	public String getCOLLECTION_PLATFORM() {
		return COLLECTION_PLATFORM;
	}
	public void setCOLLECTION_PLATFORM(String cOLLECTION_PLATFORM) {
		COLLECTION_PLATFORM = cOLLECTION_PLATFORM;
	}
	@Override
	public String toString() {
		return "LU_Collection_Type [COLLECTION_TYPE_IND=" + COLLECTION_TYPE_IND
				+ ", COLLECTION_PLATFORM=" + COLLECTION_PLATFORM + "]";
	}
}
