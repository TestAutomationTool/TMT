/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Event_State {
private String EventState;
private String EventStateId;
@Override
public String toString() {
	return "LU_Event_State [EventState=" + EventState + ", EventStateId="
			+ EventStateId + "]";
}
public String getEventState() {
	return EventState;
}
public void setEventState(String eventState) {
	EventState = eventState;
}
public String getEventStateId() {
	return EventStateId;
}
public void setEventStateId(String eventStateId) {
	EventStateId = eventStateId;
}
}
