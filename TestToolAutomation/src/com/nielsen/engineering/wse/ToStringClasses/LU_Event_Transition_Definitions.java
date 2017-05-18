/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Event_Transition_Definitions {

	private String EventTransitionID;
	private String EventTransitionName;
	@Override
	public String toString() {
		return "LU_Event_Transition_Definitions [EventTransitionID="
				+ EventTransitionID + ", EventTransitionName="
				+ EventTransitionName + "]";
	}
	public String getEventTransitionID() {
		return EventTransitionID;
	}
	public void setEventTransitionID(String eventTransitionID) {
		EventTransitionID = eventTransitionID;
	}
	public String getEventTransitionName() {
		return EventTransitionName;
	}
	public void setEventTransitionName(String eventTransitionName) {
		EventTransitionName = eventTransitionName;
	}
}
