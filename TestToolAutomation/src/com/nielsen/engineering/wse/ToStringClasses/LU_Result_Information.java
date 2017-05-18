/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Result_Information {

	private String ResultID;
	private String CollectionTypeIND;
	private String ResultName;
	public String getResultID() {
		return ResultID;
	}
	public void setResultID(String resultID) {
		ResultID = resultID;
	}
	@Override
	public String toString() {
		return "LU_Result_Information [ResultID=" + ResultID
				+ ", CollectionTypeIND=" + CollectionTypeIND + ", ResultName="
				+ ResultName + "]";
	}
	public String getCollectionTypeIND() {
		return CollectionTypeIND;
	}
	public void setCollectionTypeIND(String collectionTypeIND) {
		CollectionTypeIND = collectionTypeIND;
	}
	public String getResultName() {
		return ResultName;
	}
	public void setResultName(String resultName) {
		ResultName = resultName;
	}
}
