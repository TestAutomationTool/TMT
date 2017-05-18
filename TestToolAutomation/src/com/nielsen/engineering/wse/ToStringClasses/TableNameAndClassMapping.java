/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class TableNameAndClassMapping {
	private String ClassID;
	private String ParentTableName;
	private String InsertTableName;
	private String ClassName;
	public String getClassID() {
		return ClassID;
	}
	public void setClassID(String classID) {
		ClassID = classID;
	}
	public String getParentTableName() {
		return ParentTableName;
	}
	public void setParentTableName(String parentTableName) {
		ParentTableName = parentTableName;
	}
	public String getInsertTableName() {
		return InsertTableName;
	}
	public void setInsertTableName(String insertTableName) {
		InsertTableName = insertTableName;
	}
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	@Override
	public String toString() {
		return "TableNameAndClassMapping [ClassID=" + ClassID
				+ ", ParentTableName=" + ParentTableName + ", InsertTableName="
				+ InsertTableName + ", ClassName=" + ClassName + "]";
	}

}
