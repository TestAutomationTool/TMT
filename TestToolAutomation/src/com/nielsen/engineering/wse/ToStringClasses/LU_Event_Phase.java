/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Event_Phase {
private String EventPhase;
private String EventPhaseId;
@Override
public String toString() {
	return "LU_Event_Phase [EventPhase=" + EventPhase + ", EventPhaseId="
			+ EventPhaseId + "]";
}
public String getEventPhase() {
	return EventPhase;
}
public void setEventPhase(String eventPhase) {
	EventPhase = eventPhase;
}
public String getEventPhaseId() {
	return EventPhaseId;
}
public void setEventPhaseId(String eventPhaseId) {
	EventPhaseId = eventPhaseId;
}
}
