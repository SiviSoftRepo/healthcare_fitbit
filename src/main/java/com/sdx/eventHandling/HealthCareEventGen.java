package com.sdx.eventHandling;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
	}

	public static JSONObject buildPatientDiagoniseEvent(String patient,String diagnosis,String vital,String testResult,String suffix, String location,String resStatus) throws Exception{
		JSONObject payloadJson = new JSONObject();
		//payloadJson.put("short_description",diagnosis);
		String desc = "Patient " +patient+ " located at " +location+ " Center was diagnosed with " +diagnosis+ " - " + vital +" is at " +testResult+ " " +suffix+" . Please attend ASAP - "  +resStatus;
		payloadJson.put("short_description","NM-Albuquerque Facility");
		payloadJson.put("description",desc);
		payloadJson.put("impact", "2");
		payloadJson.put("urgency", "1");
		payloadJson.put("location","NM - Lovelace Hospital");
		payloadJson.put("assignment_group", "NM-Johnson-Nursing");
		payloadJson.put("category","Patient-Health");
		payloadJson.put("caller_id", "Scot McCoy");
		System.out.println("payloadJson :::::::::::::" + payloadJson);
		ServiceNowSim.producerTemplate = ServiceNowSim.camelContext.createProducerTemplate();
		ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.SEDA_START_ROUTE,
				payloadJson, "Event", "HealthCareEvent");
		return payloadJson;

	}
	
	
	/*
	 * public static JSONObject buildPatientDiagoniseEvent(String patient,String
	 * diagnosis,String vital,String testResult,String suffix, String
	 * location,String resStatus) throws Exception{ JSONObject payloadJson = new
	 * JSONObject(); //payloadJson.put("short_description",diagnosis); String desc =
	 * "Patient " +patient+ " located at " +location+ " Center was diagnosed with "
	 * +diagnosis+ " - " + vital +" is at " +testResult+ " "
	 * +suffix+" . Please attend ASAP - " +resStatus;
	 * payloadJson.put("short_description","NY HomeCare patient, NY-Carona Facility"
	 * ); payloadJson.put("description",desc); payloadJson.put("impact", "2");
	 * payloadJson.put("urgency", "1");
	 * payloadJson.put("location","NY Presbyterian Hospital");
	 * payloadJson.put("assignment_group", "NY-Carona-Nursing");
	 * payloadJson.put("category","Patient-Health"); payloadJson.put("caller_id",
	 * "Scot McCoy"); System.out.println("payloadJson :::::::::::::" + payloadJson);
	 * ServiceNowSim.producerTemplate =
	 * ServiceNowSim.camelContext.createProducerTemplate();
	 * ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
	 * SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent"); return
	 * payloadJson;
	 * 
	 * }
	 */
	
	/*
	 * public static JSONObject buildPatientDiagoniseEvent(String patient,String
	 * diagnosis,String vital,String testResult,String suffix, String
	 * location,String resStatus) throws Exception{ JSONObject payloadJson = new
	 * JSONObject(); //payloadJson.put("short_description",diagnosis); String desc =
	 * "Patient " +patient+ " located at " +location+ " Center was diagnosed with "
	 * +diagnosis+ " - " + vital +" is at " +testResult+ " "
	 * +suffix+" . Please attend ASAP - " +resStatus;
	 * payloadJson.put("short_description","NJ Lyndhurst-02 Facility");
	 * payloadJson.put("description",desc); payloadJson.put("impact", "2");
	 * payloadJson.put("urgency", "1");
	 * payloadJson.put("location","NJ Robert Johnson Hospital");
	 * payloadJson.put("assignment_group", "NJ-Lyndhurst-Doctors");
	 * payloadJson.put("category","Patient-Health"); payloadJson.put("caller_id",
	 * "Scot McCoy"); System.out.println("payloadJson :::::::::::::" + payloadJson);
	 * ServiceNowSim.producerTemplate =
	 * ServiceNowSim.camelContext.createProducerTemplate();
	 * ServiceNowSim.producerTemplate.requestBodyAndHeader(SedaEventPush.
	 * SEDA_START_ROUTE, payloadJson, "Event", "HealthCareEvent"); return
	 * payloadJson;
	 * 
	 * }
	 */

	public static void buildmatHttps(String PatientName, String OnBed, String BedPosition) { 
		
		OnBed = "\"" +OnBed+"\"";//Default No Patient
		//PatientName = "\"" +PatientName+"\""; 
		BedPosition = "\"" +BedPosition+"\"";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM:dd:yyyy-HH:mm:ss");
		String date = "\"" +simpleDateFormat.format(new Date())+"\"";
		
		HttpPost post = new HttpPost("https://qa.servicedx.com/subscription/events/execute");
		
		post.addHeader("username", "SuperAdmin");
		post.addHeader("content-type", "application/json");
		
	String payload = "{\"application\":\"HealthCare-IOT\","
			+ "\"event\":\"BedEvent\",\"asset\":\"Bed001\","
			+ "\"tenant\":\"servicedx_qa\","
			+ "\"variables\":{\"date\":" + date + ","
			+ "\"OnBed\":" + OnBed + ",\"BedPosition\":" + BedPosition + "}}";

		post.setEntity(new StringEntity(payload, Charset.defaultCharset()));
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println("response.getAllHeaders() " + Arrays.asList(
					
					response.getStatusLine() + "*****************************************************************"));
			// result = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} 
		
	}


}
