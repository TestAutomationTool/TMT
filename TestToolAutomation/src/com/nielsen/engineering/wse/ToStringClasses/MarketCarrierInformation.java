/**
 * 
 */
package com.nielsen.engineering.wse.ToStringClasses;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class MarketCarrierInformation {	
	private String PORT;
	private String CARRIER_NAME;
	private String DEVICE_TECHNOLOGY_NAME;	
	public String getPORT() {
		return PORT;
	}
	public void setPORT(String pORT) {
		PORT = pORT;
	}
	public String getCARRIER_NAME() {
		return CARRIER_NAME;
	}
	@Override
	public String toString() {
		return "MarketCarrierInformation [PORT=" + PORT + ", CARRIER_NAME="
				+ CARRIER_NAME + ", DEVICE_TECHNOLOGY_NAME="
				+ DEVICE_TECHNOLOGY_NAME + ", SUPPORTED_TECHNOLOGY="
				+ SUPPORTED_TECHNOLOGY + "]";
	}
	public void setCARRIER_NAME(String cARRIER_NAME) {
		CARRIER_NAME = cARRIER_NAME;
	}
	public String getDEVICE_TECHNOLOGY_NAME() {
		return DEVICE_TECHNOLOGY_NAME;
	}
	public void setDEVICE_TECHNOLOGY_NAME(String dEVICE_TECHNOLOGY_NAME) {
		DEVICE_TECHNOLOGY_NAME = dEVICE_TECHNOLOGY_NAME;
	}
	public String getSUPPORTED_TECHNOLOGY() {
		return SUPPORTED_TECHNOLOGY;
	}
	public void setSUPPORTED_TECHNOLOGY(String sUPPORTED_TECHNOLOGY) {
		SUPPORTED_TECHNOLOGY = sUPPORTED_TECHNOLOGY;
	}
	private String SUPPORTED_TECHNOLOGY;

}
