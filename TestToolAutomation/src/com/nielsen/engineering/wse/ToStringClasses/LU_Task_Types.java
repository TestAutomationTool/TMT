/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Task_Types {
	public String getTaskType() {
		return TaskType;
	}
	public void setTaskType(String taskType) {
		TaskType = taskType;
	}
	public String getTaskId() {
		return TaskId;
	}
	public void setTaskId(String taskId) {
		TaskId = taskId;
	}
	private String TaskType;
	@Override
	public String toString() {
		return "LU_Task_Types [TaskType=" + TaskType + ", TaskId=" + TaskId
				+ "]";
	}
	private String TaskId;

}
