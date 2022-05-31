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

public class StepHttp {

	public static void main(String[] args) throws IOException {
//LinkedHashMap<String, Object> step
		sendPOST();
	}

	public static void sendPOST() throws IOException {

		/*
		 * System.out.println(
		 * "000000000000000000000000000000000asdasdasdasdasdasdasdasdasdasd" + step);
		 * String stepHas = step.get("activities-tracker-steps").toString();
		 * System.out.println(
		 * "000000000000000000000000000000000asdasdasdasdasdasdasdasdasdasd"); try {
		 * System.out.println(
		 * "000000000000000000000000000000000asdasdasdasdasdasdasdasdasdasd");
		 * Map<String, String> map = new HashMap<>();
		 * 
		 * String onObjectValue = stepHas;
		 * 
		 * onObjectValue = onObjectValue.substring(1, onObjectValue.length() - 1);
		 * String[] keyValuePairs = onObjectValue.split(",");
		 * 
		 * for (String pair : keyValuePairs) { try { String[] entry = pair.split("=");
		 * map.put(entry[0].trim(), entry[1].trim()); // add them to the hashmap and
		 * trim whitespaces
		 * 
		 * } catch (Exception e) { } }
		 */

		final String uri = "https://api.fitbit.com/1/user/-/activities/floors/date/today/1d.json";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkJIN0giLCJzdWIiOiI4QkpaQlkiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd251dCB3cHJvIHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNjI4ODM2MTQ0LCJpYXQiOjE2MjgyMzcxOTd9.JF8AcJlES_vJIwDnl4VcFlM8z408Gl11ZdBF7A7DlKc");
		// headers.set("X-COM-LOCATION", "USA");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		System.out.println("STEPSSSSS");

		JSONObject responseJson = new JSONObject(response.getBody());

		String stepActivity = responseJson.get("activities-floors").toString();

		JSONArray array = new JSONArray(stepActivity);

		JSONObject activityValues = array.getJSONObject(0);

		String result = "";
		HttpPost post = new HttpPost("https://qa.servicedx.com/subscription/events/execute");

		post.addHeader("username", "SuperAdmin");
		post.addHeader("content-type", "application/json");

		String dateTime = activityValues.getString("dateTime");
		String name = "Narendran";
		String stepFromJson = activityValues.getString("value");
		String macId = "5115B569D3C5";
		int stepValue = Integer.parseInt(stepFromJson);

		// add request parameters or form parameters

		JSONObject obj = new JSONObject();

		JSONObject obj1 = new JSONObject();
		obj1.put("date", dateTime);
		obj1.put("deviceId", macId);
		obj1.put("location", "New York");
		obj1.put("patientName", "Narendran");
		obj1.put("floor", stepValue);
		obj.put("application", "Fitbit_Care");
		obj.put("event", "Fitbit");
		obj.put("asset", "FB-Classic-0001");
		obj.put("variables", obj1);

		post.setEntity(new StringEntity(obj.toString(), ContentType.APPLICATION_JSON));

		System.out.println("000000000000000000000000000000000asdasdasdasdasdasdasdasdasdasd");
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response1 = httpClient.execute(post)) {

			System.out.println("response.getAllHeaders() " + Arrays.asList(
					response1.getStatusLine() + "*****************************************************************"));
			// result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
		}
	}
}
