/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class MD_TestCase_Status {
	private String StatusId;
	private String StatusComments;

	public String getStatusId() {
		return StatusId;
	}

	public void setStatusId(String statusId) {
		StatusId = statusId;
	}

	public String getStatusComments() {
		return StatusComments;
	}

	public void setStatusComments(String statusComments) {
		StatusComments = statusComments;
	}

	@Override
	public String toString() {
		return "MD_TestCase_Status [StatusId=" + StatusId + ", StatusComments="
				+ StatusComments + "]";
	}
}
