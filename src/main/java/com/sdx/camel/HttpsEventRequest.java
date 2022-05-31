package com.sdx.camel;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpsEventRequest {
	public static void main(String[] args) throws IOException {

		try {

			String result = sendPOST("https://dev66250.service-now.com/api/now/table/incident");
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String sendPOST(String url) throws IOException {

		String result = "";
		HttpPost post = new HttpPost(url);
		post.addHeader("userId", "1001");
		post.addHeader("userName", "sdxuser");
		post.addHeader("content-type", "application/json");

		// add request parameters or form parameters

		// String test = "{ \"applicationId\": 100, \"applicationName\":
		// \"stampingMachine\", \"assetId\": 291297, \"assetName\": \"stampingCamera\",
		// \"eventId\": 130897, \"eventName\": \"stampingCounter\", \"eventRecordId\":
		// \"465464\", \"eventStatus\": \"yes\", \"payload\": \"\"Counter Recognition :
		// 22057\", \"tenantId\": \"ServiceDx\" }";
		String test =
				
				  "{ \"short_description\": \"IOT was device down for more than hour\",\r\n" + "  \"description\": \"IOT DEVICE DEVICE0001 was down from 07:00 AM\",\r\n"+
				  "  \"impact\": \"1\"\r\n" +
				  "  \"urgency\": 1\r\n" + "\"  \"assignment_group\": \"Hardware\"\r\n"+ "\"caller_id\": \"  ServiceDX ServiceDX\"\r\n"+"}";

		   
		
		
		
		System.out.println("Test "+test);
		//System.out.println("Test "+test2);
		
		//post.setEntity(new StringEntity(test2, Charset.defaultCharset()));
		System.out.println(":::::::::values" + post);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println("response.getAllHeaders() " + Arrays.asList(
					response.getStatusLine() + "*****************************************************************"));
			// result = EntityUtils.toString(response.getEntity());
		}

		return result;
	}
	
}
