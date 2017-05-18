/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class LU_Event_Info {
	private String collection_type;
	private String EVENT_NAME;
	private String MODE;
	private String TEXTFILE_NAME;
	private String EVENT_ID;

	public String getCollection_type() {
		return collection_type;
	}

	public void setCollection_type(String collection_type) {
		this.collection_type = collection_type;
	}

	public String getEVENT_NAME() {
		return EVENT_NAME;
	}

	public void setEVENT_NAME(String eVENT_NAME) {
		EVENT_NAME = eVENT_NAME;
	}

	public String getMODE() {
		return MODE;
	}

	public void setMODE(String mODE) {
		MODE = mODE;
	}

	public String getTEXTFILE_NAME() {
		return TEXTFILE_NAME;
	}

	public void setTEXTFILE_NAME(String tEXTFILE_NAME) {
		TEXTFILE_NAME = tEXTFILE_NAME;
	}

	public String getEVENT_ID() {
		return EVENT_ID;
	}

	public void setEVENT_ID(String eVENT_ID) {
		EVENT_ID = eVENT_ID;
	}

	@Override
	public String toString() {
		return "LU_Event_Info [collection_type=" + collection_type
				+ ", EVENT_NAME=" + EVENT_NAME + ", MODE=" + MODE
				+ ", TEXTFILE_NAME=" + TEXTFILE_NAME + ", EVENT_ID=" + EVENT_ID
				+ "]";
	}
}
