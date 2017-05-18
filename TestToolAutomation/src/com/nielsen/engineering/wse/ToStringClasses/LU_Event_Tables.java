/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class LU_Event_Tables {
	private String COLLECTION_TYPE;
	private String COLUMN_NAME;
	private String TEXTFILE_NAME;
	public String getCOLLECTION_TYPE() {
		return COLLECTION_TYPE;
	}
	public void setCOLLECTION_TYPE(String cOLLECTION_TYPE) {
		COLLECTION_TYPE = cOLLECTION_TYPE;
	}
	public String getCOLUMN_NAME() {
		return COLUMN_NAME;
	}
	public void setCOLUMN_NAME(String cOLUMN_NAME) {
		COLUMN_NAME = cOLUMN_NAME;
	}
	public String getTEXTFILE_NAME() {
		return TEXTFILE_NAME;
	}
	public void setTEXTFILE_NAME(String tEXTFILE_NAME) {
		TEXTFILE_NAME = tEXTFILE_NAME;
	}
	@Override
	public String toString() {
		return "LU_Event_Tables [COLLECTION_TYPE=" + COLLECTION_TYPE
				+ ", COLUMN_NAME=" + COLUMN_NAME + ", TEXTFILE_NAME="
				+ TEXTFILE_NAME + "]";
	}

}
