package com.sdx.camel;

import org.json.JSONException;
import org.json.JSONObject;

public class PerformanceEventGen {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//for (int i = 0; i < 23; i++) {
           
			RunSedaApp.producerTemplate = RunSedaApp.camelContext.createProducerTemplate();
			RunSedaApp.producerTemplate.requestBodyAndHeader(SedaEndPointRouter.SEDA_START_ROUTE,
					buildPerformancePayload(), "OEE", "MachinePerformance");
		//}
		// RunSedaApp.buildEvent(buildPerformancePayload());
	}

	public static JSONObject buildPerformancePayload() throws JSONException {
		JSONObject payloadJson = new JSONObject();
		 System.out.println("//////////////////////////////////In Performance?????????????????????????????");
		payloadJson.put("short_description", "IOT was device down for more than hour");
		payloadJson.put("description", "IOT DEVICE DEVICE0001 was down from 07:00 AM");
		payloadJson.put("impact", "1");
		payloadJson.put("urgency", "1");
		payloadJson.put("assignment_group", "Hardware");
		payloadJson.put("caller_id", "ServiceDX ServiceDX");
		System.out.println("payloadJson :::::::::::::" + payloadJson);

		return payloadJson;

	}

}
