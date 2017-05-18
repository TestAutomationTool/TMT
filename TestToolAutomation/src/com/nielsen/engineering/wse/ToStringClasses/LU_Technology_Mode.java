/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 *
 */
public class LU_Technology_Mode {
	private String TechnologyMode;
	private String TechnologyModeId;
	public String getTechnologyMode() {
		return TechnologyMode;
	}
	public void setTechnologyMode(String technologyMode) {
		TechnologyMode = technologyMode;
	}
	public String getTechnologyModeId() {
		return TechnologyModeId;
	}
	public void setTechnologyModeId(String technologyModeId) {
		TechnologyModeId = technologyModeId;
	}
	@Override
	public String toString() {
		return "LU_Technology_Mode [TechnologyMode=" + TechnologyMode
				+ ", TechnologyModeId=" + TechnologyModeId + "]";
	}

}
