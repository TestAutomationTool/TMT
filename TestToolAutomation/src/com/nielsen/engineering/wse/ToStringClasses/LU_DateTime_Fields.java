/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class LU_DateTime_Fields {
	private String Textfile_name;
	private String Column_name;

	public String getTextfile_name() {
		return Textfile_name;
	}

	public void setTextfile_name(String textfile_name) {
		Textfile_name = textfile_name;
	}

	public String getColumn_name() {
		return Column_name;
	}

	public void setColumn_name(String column_name) {
		Column_name = column_name;
	}

	@Override
	public String toString() {
		return "LU_DateTime_Fields [Textfile_name=" + Textfile_name
				+ ", Column_name=" + Column_name + "]";
	}
}
