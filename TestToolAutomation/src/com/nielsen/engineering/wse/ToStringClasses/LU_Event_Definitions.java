/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Event_Definitions {
	private String EventID;
	private String EventName;
	public String getEventID() {
		return EventID;
	}
	public void setEventID(String eventID) {
		EventID = eventID;
	}
	public String getEventName() {
		return EventName;
	}
	public void setEventName(String eventName) {
		EventName = eventName;
	}
	@Override
	public String toString() {
		return "LU_Event_Definitions [EventID=" + EventID + ", EventName="
				+ EventName + "]";
	}
}
