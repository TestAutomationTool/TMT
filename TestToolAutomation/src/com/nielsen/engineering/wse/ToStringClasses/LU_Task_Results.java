/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Task_Results {
	 
	private String TASK_RESULTS_CLASSIFICATION;
	private String TASK_RESULTS_CLASSIFICATION_ID;
	private String TASK_RESULTS;
	private String TASK_RESULTS_ID;
	private String TASK_RESULTS_CODE;
	public String getTASK_RESULTS_CLASSIFICATION() {
		return TASK_RESULTS_CLASSIFICATION;
	}
	public void setTASK_RESULTS_CLASSIFICATION(String tASK_RESULTS_CLASSIFICATION) {
		TASK_RESULTS_CLASSIFICATION = tASK_RESULTS_CLASSIFICATION;
	}
	public String getTASK_RESULTS_CLASSIFICATION_ID() {
		return TASK_RESULTS_CLASSIFICATION_ID;
	}
	public void setTASK_RESULTS_CLASSIFICATION_ID(
			String tASK_RESULTS_CLASSIFICATION_ID) {
		TASK_RESULTS_CLASSIFICATION_ID = tASK_RESULTS_CLASSIFICATION_ID;
	}
	public String getTASK_RESULTS() {
		return TASK_RESULTS;
	}
	public void setTASK_RESULTS(String tASK_RESULTS) {
		TASK_RESULTS = tASK_RESULTS;
	}
	@Override
	public String toString() {
		return "LU_Task_Results [TASK_RESULTS_CLASSIFICATION="
				+ TASK_RESULTS_CLASSIFICATION
				+ ", TASK_RESULTS_CLASSIFICATION_ID="
				+ TASK_RESULTS_CLASSIFICATION_ID + ", TASK_RESULTS="
				+ TASK_RESULTS + ", TASK_RESULTS_ID=" + TASK_RESULTS_ID
				+ ", TASK_RESULTS_CODE=" + TASK_RESULTS_CODE + "]";
	}
	public String getTASK_RESULTS_ID() {
		return TASK_RESULTS_ID;
	}
	public void setTASK_RESULTS_ID(String tASK_RESULTS_ID) {
		TASK_RESULTS_ID = tASK_RESULTS_ID;
	}
	public String getTASK_RESULTS_CODE() {
		return TASK_RESULTS_CODE;
	}
	public void setTASK_RESULTS_CODE(String tASK_RESULTS_CODE) {
		TASK_RESULTS_CODE = tASK_RESULTS_CODE;
	}
	}
