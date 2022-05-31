package com.sdx.eventHandling;

import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPStatement;

public class EsperUnit {
	
	private String unitUUID				= null;
	
	private EPDeployment epDeployment	= null;
	
	private EPStatement statement 		= null;
	
	private String dashWidgetUUID		= null;
	
	

	public String getUnitUUID() {
		return unitUUID;
	}

	public void setUnitUUID(String unitUUID) {
		this.unitUUID = unitUUID;
	}

	public EPDeployment getEpDeployment() {
		return epDeployment;
	}

	public void setEpDeployment(EPDeployment epDeployment) {
		this.epDeployment = epDeployment;
	}

	public EPStatement getStatement() {
		return statement;
	}

	public void setStatement(EPStatement statement) {
		this.statement = statement;
	}

	public String getDashWidgetUUID() {
		return dashWidgetUUID;
	}

	public void setDashWidgetUUID(String dashWidgetUUID) {
		this.dashWidgetUUID = dashWidgetUUID;
	}

}
