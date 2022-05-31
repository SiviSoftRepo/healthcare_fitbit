package com.sdx.eventHandling;

import java.util.Map;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

//import javax.servlet.http.HttpServletResponse;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.HttpRequest;
import org.json.JSONObject;
//import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SedaEventPush extends RouteBuilder {

	static Logger LOG = LoggerFactory.getLogger(SedaEventPush.class);

	public static final String SEDA_START_ROUTE = "seda:start";
	public static final String SEDA_END_ROUTE = "seda:end";
	public static String HTTP_URL = "https://dev66250.service-now.com/";
	public static int threadCnt = 20;
	public static int macPerfCount = 0;
	public static int macQualityCount = 0;

	@Override
	public void configure() throws Exception {
		System.out.println("Event  process startred ::::::::::::::::::::");
		from(SEDA_START_ROUTE).routeId("EventDistribution")

				.threads().executorService(Executors.newFixedThreadPool(threadCnt)).process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						// Object eventObj = exchange.getIn().getBody();
						Object eventObj = exchange.getIn().getBody();
						// System.out.println("Process started" + eventObj);

						HTTP_URL = "https://dev66250.service-now.com/";
						System.out.println(" Url for connecting SDX ::::::::::::" + HTTP_URL);
						// System.out.println("macPerfCount ::::::::::::::::"+macPerfCount);
					}
				}).setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader("userid", constant("servicedx")).convertBodyTo(String.class)
				.setHeader("password", constant("sivisoft@123")).convertBodyTo(String.class)
				//.setHeader("Authorization", constant("Basic c2R4dXNlcjpzZHh1c2Vy")).convertBodyTo(String.class)
				.toD(HTTP_URL).process(new Processor() {
					public void process(Exchange exchange) throws Exception { // TODO

						HttpRequest response = (HttpRequest) exchange.getIn().getBody(HttpServletResponse.class);
						Map<String, Object> res = exchange.getIn().getHeaders();

						System.out.println("response ::::::::::" + exchange.getIn().getBody(String.class));
						System.out.println(
								"httpRespCode ::::::::::" + exchange.getIn().getHeader("CamelHttpResponseCode"));
						if(exchange.getIn().getHeader("CamelHttpResponseCode").toString().equals("201")) {
							System.out.println(" Successfully Event Pushed to Service Now  :::::::::::::");
							
						} else {
							System.out.println(" Event pushing failed  :::::::::::::");
						}
						
					}
				}).end();

	}

}