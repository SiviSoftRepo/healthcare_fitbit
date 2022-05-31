package com.sdx.eventtypes;

import org.joda.time.DateTime;

public class HCBaseEvent {

	private DateTime timestamp 			= null;
	
	private String resourceIdentifier	= null;
	
	private String rawText				= null;
	
	
	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
	}
	
}
