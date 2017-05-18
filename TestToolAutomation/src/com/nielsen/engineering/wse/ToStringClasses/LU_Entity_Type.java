/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Entity_Type {
	
	private String EntityName;
	private String EntityExample;
	private String EntityPosition;
	@Override
	public String toString() {
		return "LU_Entity_Type [EntityName=" + EntityName + ", EntityExample="
				+ EntityExample + ", EntityPosition=" + EntityPosition + "]";
	}
	public String getEntityName() {
		return EntityName;
	}
	public void setEntityName(String entityName) {
		EntityName = entityName;
	}
	public String getEntityExample() {
		return EntityExample;
	}
	public void setEntityExample(String entityExample) {
		EntityExample = entityExample;
	}
	public String getEntityPosition() {
		return EntityPosition;
	}
	public void setEntityPosition(String entityPosition) {
		EntityPosition = entityPosition;
	}

}
