package com.sdx.eventtypes;

import com.alibaba.fastjson.JSONObject;

public class AnonymousEvent extends JSONObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String eventUUID = null;

	public String getEventUUID() {
		return eventUUID;
	}

	public void setEventUUID(String eventUUID) {
		this.eventUUID = eventUUID;
	}

}
