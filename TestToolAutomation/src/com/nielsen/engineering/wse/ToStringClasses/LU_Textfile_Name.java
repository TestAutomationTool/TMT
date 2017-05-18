/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Textfile_Name {
	private String TextFileName;
	private String TextFileId;
	public String getTextFileName() {
		return TextFileName;
	}
	public void setTextFileName(String textFileName) {
		TextFileName = textFileName;
	}
	public String getTextFileId() {
		return TextFileId;
	}
	public void setTextFileId(String textFileId) {
		TextFileId = textFileId;
	}
	@Override
	public String toString() {
		return "LU_Textfile_Name [TextFileName=" + TextFileName
				+ ", TextFileId=" + TextFileId + "]";
	}

}
