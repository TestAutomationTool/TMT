/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class LU_TextFile_Collection_Type {
	private String TextFileName;
	private String CollectionType;

	public String getTextFileName() {
		return TextFileName;
	}

	public void setTextFileName(String textFileName) {
		TextFileName = textFileName;
	}

	public String getCollectionType() {
		return CollectionType;
	}

	public void setCollectionType(String collectionType) {
		CollectionType = collectionType;
	}

	@Override
	public String toString() {
		return "LU_TextFile_Collection_Type [TextFileName=" + TextFileName
				+ ", CollectionType=" + CollectionType + "]";
	}
}
