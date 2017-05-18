/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class StagingTablesList {
	
	private String TableId;
	private String TableName;
	public String getTableId() {
		return TableId;
	}
	public void setTableId(String tableId) {
		TableId = tableId;
	}
	@Override
	public String toString() {
		return "StagingTablesList [TableId=" + TableId + ", TableName="
				+ TableName + "]";
	}
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	 
}
