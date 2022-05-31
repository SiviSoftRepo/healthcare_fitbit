package com.sdx.camel;

import org.json.JSONException;
import org.json.JSONObject;

public class QualityEventGen {

	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		RunSedaApp.producerTemplate = RunSedaApp.camelContext.createProducerTemplate();
		RunSedaApp.producerTemplate.requestBodyAndHeader(SedaEndPointRouter.SEDA_START_ROUTE,buildPerformancePayload(),"OEE","MachineQuality"); 
		
		//RunSedaApp.buildEvent(buildPerformancePayload());
	}
	 
	
	public static JSONObject buildPerformancePayload() throws JSONException {
		JSONObject payloadJson = new JSONObject();

		payloadJson.put("shiftNo", "1");
		payloadJson.put("stationCode",  "TAD/EQP/084");
		payloadJson.put("stationName", "STAMPING M/C 1");
		payloadJson.put("goodCount", 10);
		System.out.println("Machine payloadJson :::::::::::::"+payloadJson);
		
		
		return payloadJson;
		
	}

}
