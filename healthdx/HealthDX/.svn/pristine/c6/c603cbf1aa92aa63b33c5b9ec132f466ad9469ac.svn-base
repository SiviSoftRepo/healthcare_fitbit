package com.sdx.eventHandling;

import org.json.JSONObject;

public class HealthCareEventGen {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//for (int i = 0; i < 23; i++) {

		/*
		 * ServiceNowSim.producerTemplate =
		 * ServiceNowSim.camelContext.createProducerTemplate();
		 * ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 * SEDA_START_ROUTE, buildPatientDiagoniseEvent(), "Event", "HealthCareEvent");
		 */	//}
		// RunSedaApp.buildEvent(buildPerformancePayload());
		
		buildPatientDiagoniseEventTest3();
	}

	public static JSONObject buildPatientDiagoniseEvent(String patient,String diagnosis,String vital,String testResult,String suffix, String location,String resStatus) throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = "Patient " +patient+ " located at " +location+ " Center was diagnosed with " +diagnosis+ " - " + vital +" is at " +testResult+ " " +suffix+" . Please attend ASAP - "  +resStatus;
		payloadJson.put("short_description","NM-Albuquerque Facility");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "2");
		payloadJson.put("urgency", "1");
		payloadJson.put("subcategory", "Patient Disease");
		payloadJson.put("location","NM - Lovelace Hospital");
		payloadJson.put("assignment_group", "NM-Johnson-Nursing");
		payloadJson.put("category","Patient-Health");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		/*
		 * ServiceNowSim.producerTemplate =
		 * ServiceNowSim.camelContext.createProducerTemplate();
		 * ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 * SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent");
		 */
		return payloadJson;

	}
	
	public static JSONObject buildPatientDiagoniseEventTest() throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = " Patient John Dunsford from room no. 102 has fallen of the bed.Kindly request your urgent attention for the same";
		payloadJson.put("short_description","Patient fallen off bed");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "1");
		payloadJson.put("urgency", "2");
		payloadJson.put("subcategory", "Patient Bed");
		payloadJson.put("location","NY-Manhattan-Healthcare");
		payloadJson.put("assignment_group", "NY-ManhattanHC-Nursing");
		payloadJson.put("category","Patient-Status");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		
		  ServiceNowSim.producerTemplate =
		  ServiceNowSim.camelContext.createProducerTemplate();
		  ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent");
		 
		return payloadJson;

	}
	
	
	public static JSONObject buildPatientDiagoniseEventTest1() throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = "Patient Lisa Beth from room no. 206 has showed no movements from past few hours, kindly request you to attend the same";
		payloadJson.put("short_description","no movement for last few hours");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "2");
		payloadJson.put("urgency", "2");
		payloadJson.put("subcategory", "Patient Bed");
		payloadJson.put("location","NY-Queens-Healthcare");
		payloadJson.put("assignment_group", "NY-QueensHC-Nursing");
		payloadJson.put("category","Patient-Status");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		
		  ServiceNowSim.producerTemplate =
		  ServiceNowSim.camelContext.createProducerTemplate();
		  ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent");
		 
		return payloadJson;

	}
	
	public static JSONObject buildPatientDiagoniseEventTest2() throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = "Baby Kim Luis has been having temperature 102 degrees from past two days.Kindly request you to prescribe the right medication for the same";
		payloadJson.put("short_description","temperature not reducing from 102 degrees");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "1");
		payloadJson.put("urgency", "2");
		payloadJson.put("subcategory", "Remote monitoring");
		payloadJson.put("location","NY-Queens-Healthcare");
		payloadJson.put("assignment_group", "NY-QueensHC-Doctors");
		payloadJson.put("category","Patient-Health");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		
		  ServiceNowSim.producerTemplate =
		  ServiceNowSim.camelContext.createProducerTemplate();
		  ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent");
		 
		return payloadJson;

	}
	
	public static JSONObject buildPatientDiagoniseEventTest3() throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = "Patient Williams has been suffering from gastric issue and has pain kindly advice the right medication for immediate relief";
		payloadJson.put("short_description","Gastric issue and pain");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "2");
		payloadJson.put("urgency", "2");
		payloadJson.put("subcategory", "Remote monitoring");
		payloadJson.put("location","NY-Brooklyn-Healthcare");
		payloadJson.put("assignment_group", "NY-BrooklynHC-Doctors");
		payloadJson.put("category","Patient-Health");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		
		  ServiceNowSim.producerTemplate =
		  ServiceNowSim.camelContext.createProducerTemplate();
		  ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent");
		 
		return payloadJson;

	}
	
	public static JSONObject buildPatientDiagoniseEventTest4() throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = "Attn: Team, Oxygen leave in Presbyterian Hospital Pediatric ICU 2FL, Right Wing is very low .Kindly request you to replace the same at your earliest";
		payloadJson.put("short_description","Oxygen level low");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "1");
		payloadJson.put("urgency", "2");
		payloadJson.put("subcategory", "Oxygen Cylinder");
		payloadJson.put("location","NY-Queens-Healthcare");
		payloadJson.put("assignment_group", "NY-QueensHC-Maintenance");
		payloadJson.put("category","Medical-Device");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		
		  ServiceNowSim.producerTemplate =
		  ServiceNowSim.camelContext.createProducerTemplate();
		  ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent");
		 
		return payloadJson;

	}
	
	public static JSONObject buildPatientDiagoniseEventTest5() throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = "Attn : Team , Oximeter needs to be calibrated in NJ Robert Johnson Hospital, Room 811 on or before 20th Feb 2020";
		payloadJson.put("short_description","Oximeter needs to be calibrated by Feb 20, 2020");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "3");
		payloadJson.put("urgency", "2");
		payloadJson.put("subcategory", "Oximeter");
		payloadJson.put("location","NY-Brooklyn-Healthcare");
		payloadJson.put("assignment_group", "NY-BrooklynHC-Maintenance");
		payloadJson.put("category","Medical-Device");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		
		  ServiceNowSim.producerTemplate =
		  ServiceNowSim.camelContext.createProducerTemplate();
		  ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
		 SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent");
		 
		return payloadJson;

	}

	


}
