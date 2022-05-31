package com.sdx.camel;


import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

public class HeartHttp {

	public static void main(String[] args) throws IOException {

	}

	public static void sendPOST(HashMap heart) throws IOException, Exception {

		
	
	     
		
		
		try {

			Map<String, String> map = new HashMap<>();

			String onObjectValue = heart.values().toString();

			onObjectValue = onObjectValue.substring(1, onObjectValue.length() - 1);
			String[] keyValuePairs = onObjectValue.split(",");

			for (String pair : keyValuePairs) {
				try {
					String[] entry = pair.split("=");
					map.put(entry[0].trim(), entry[1].trim()); // add them to the hashmap and trim whitespaces

				} catch (Exception e) {
				}
			}
			
			System.out.println("HEARTTTTTTTTTTTTTTTT"+map.get("[{dateTime"));

			HttpPost post = new HttpPost("https://qa.servicedx.com/subscription/events/execute");

			post.addHeader("username", "SuperAdmin");
			post.addHeader("content-type", "application/json");

			String dateTime = map.get("[{dateTime");
			String name = "Narendran";
			String restingHeartRate = map.get("restingHeartRate");
			restingHeartRate = restingHeartRate.replace("}", "");

			// add request parameters or form parameters

			String payload = "{\"application\":\"HealthCare-IOT\",\r\n" + "\"event\":\"FITBitEvent\",\r\n"
					+ "\"asset\":\"Fitbit001\",\r\n" + "\"tenant\":\"servicedx_qa\",\r\n" + "\"variables\":{\r\n"
					+ "\"dateTime\":\"" + dateTime + "\",\r\n" + "\"patientName\":\"" + name + "\",\r\n"
					+ "\"restingHeartRate\":\"" + restingHeartRate + "\"\r\n" + "}\r\n" + "}";

			post.setEntity(new StringEntity(payload, Charset.defaultCharset()));
			

			try (CloseableHttpClient httpClient = HttpClients.createDefault();
					CloseableHttpResponse responseheart = httpClient.execute(post)) {
				
			

				System.out.println("response.getAllHeaders() " + Arrays.asList(responseheart.getStatusLine()
						+ "*****************************************************************"));
				// result = EntityUtils.toString(response.getEntity());
			}

		} catch (Exception e) {
		}

	}
}