package com.sdx.camel;



import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CaloriesHttp {

	public static void main(String[] args) throws IOException {

		sendPOST();
	}

	public static void sendPOST() throws IOException {

		/*
		 * final String uri =
		 * "https://api.fitbit.com/1/user/-/activities/calories/date/today/1d.json";
		 * RestTemplate restTemplate = new RestTemplate();
		 * 
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * headers.set("Authorization",
		 * "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkJIN0giLCJzdWIiOiI4QkpaQlkiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd251dCB3cHJvIHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNjE1NDYyMzI4LCJpYXQiOjE1ODM5MjYzMjh9.W0rnQYrbLIwX28Sa8xCgsks-0ZDWJ3y5jf8eSaEv2nw"
		 * ); // headers.set("X-COM-LOCATION", "USA");
		 * 
		 * HttpEntity<String> entity = new HttpEntity<String>(headers);
		 * 
		 * ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,
		 * entity, String.class);
		 * 
		 * System.out.println("CAloriesss");
		 * 
		 * JSONObject responseJson = new JSONObject(response.getBody());
		 * 
		 * String caloriesActivity = responseJson.get("activities-calories").toString();
		 * 
		 * JSONArray array = new JSONArray(caloriesActivity);
		 * 
		 * JSONObject activityValues = array.getJSONObject(0);
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		//Object cal =  responses.get("activities-tracker-calories");
		
		//LinkedHashMap cal1 = (LinkedHashMap) cal;
		
		
		/*try {

			Map<String, String> map = new HashMap<>();
			
			String onObjectValue = calorie;

			onObjectValue = onObjectValue.substring(1, onObjectValue.length() - 1);
			String[] keyValuePairs = onObjectValue.split(",");
			

			for (String pair : keyValuePairs) {
				try {
					String[] entry = pair.split("=");
					map.put(entry[0].trim(), entry[1].trim()); // add them to the hashmap and trim whitespaces

				} catch (Exception e) {
				}
			}
			
			System.out.println("MppPPpPpPpPiiiiiiiiiiiiii///////////////"+map.get("{dateTime"));
		
		*/
		
		
		HttpPost post = new HttpPost("https://qa.servicedx.com/subscription/events/execute");
		//post.addHeader("userId", "1001");
		post.addHeader("username", "SuperAdmin");
		post.addHeader("content-type", "application/json");
		
	
		
		String dateTime = "2021/08/07";
		String name = "Narendran";
		String calories = "1600";
		String macId = "5115B569D3C5";
		String location = "New-York";
		int caloriesValue=Integer.parseInt(calories);  
		
		// add request parameters or form parameters
		
	

		String payload = "{\"application\":\"Fitbit_Care\",\r\n" + 
				"\"event\":\"Fitbit\",\r\n" + 
				"\"asset\":\"FB-Classic-0001\",\r\n" + 
				"\"tenant\":\"servicedx_qa\",\r\n" + 
				"\"variables\":{\r\n" + 
				"\"date\":\""+dateTime+"\",\r\n" + 
				"\"deviceId\":\""+macId+"\",\r\n" +
				"\"location\":\""+location+"\",\r\n" +
				"\"patientName\":\""+name+"\",\r\n" + 
				"\"calories\":\""+caloriesValue+"\"\r\n" + 
				"}\r\n" + 
				"}";
		
		JSONObject obj = new JSONObject();
		
		JSONObject obj1 = new JSONObject();
		obj1.put("date", dateTime);
		obj1.put("deviceId", macId);
		obj1.put("location", "New York");
		obj1.put("patientName", "Narendran");
		obj1.put("calories", 1600);
	      obj.put("application", "Fitbit_Care");
	      obj.put("event", "Fitbit");
	      obj.put("asset", "FB-Classic-0001");
	      obj.put("variables", obj1);

		post.setEntity(new StringEntity(obj.toString(), ContentType.APPLICATION_JSON));
	

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response1 = httpClient.execute(post)) {
			System.out.println(":::::::::values for CALOLOLOLOOLOLO" + payload);

			System.out.println("response.getAllHeaders() " + Arrays.asList(
					response1.getStatusLine() + "*****************************************************************"));
			// result = EntityUtils.toString(response.getEntity());
		}
		catch (Exception e) {
		}
	
	
		}
}