package com.sdx.camel;

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

public class SedaEndPointRouter extends RouteBuilder {

	static Logger LOG = LoggerFactory.getLogger(SedaEndPointRouter.class);

	public static final String SEDA_START_ROUTE = "seda:start";
	public static final String SEDA_END_ROUTE = "seda:end";
	public static  String HTTP_URI = "https://dev85115.service-now.com/api/now/table/incident";
	public static int threadCnt = 20;
	public static int macPerfCount = 0;
	public static int macQualityCount = 0;

	/*
	 * @Override public void configure() throws Exception {
	 * from(DIRECT_START_ROUTE).routeId("SedaStartRouteId").setBody().
	 * simple("Seds Message").to(SEDA_END_ROUTE) .process(new Processor() { public
	 * void process(Exchange exchange) throws Exception {
	 * 
	 * LOG.info("Message at Parent route completion"); } });
	 * 
	 * from(SEDA_END_ROUTE).routeId("EndRouteId").setBody().simple("End Message").
	 * process(new Processor() { public void process(Exchange exchange) throws
	 * Exception { LOG.info("message after seda end route completion"); } }); }
	 */
	
	@Override
	public void configure() throws Exception {
		System.out.println("Seda process startred ::::::::::::::::::::");
		from(SEDA_START_ROUTE).routeId("EventDistribution")

				.threads().executorService(Executors.newFixedThreadPool(threadCnt)).process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						Object eventObj = exchange.getIn().getBody();
						System.out.println("Process started" + eventObj);
						
					
							 // PerformanceEventGen new_name = (PerformanceEventGen) eventObj;
						     System.out.println("performance instance ::::::::::");
						     HTTP_URI = "https://dev85115.service-now.com/api/now/table/incident";
						    // macPerfCount++;
						 
						  
						  System.out.println(" Url for connecting SDX ::::::::::::"+HTTP_URI);
						  //System.out.println("macPerfCount ::::::::::::::::"+macPerfCount);
					}
				})
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.HTTP_QUERY, constant("userId=123&userName=cAMEL"))
				// .delay(10000)
				// .setBody().constant(test)
				.convertBodyTo(String.class)
				 .toD(HTTP_URI)
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception { // TODO

						HttpRequest response = (HttpRequest) exchange.getIn().getBody(HttpServletResponse.class);
						Map<String, Object> res =  exchange.getIn().getHeaders();
						
						System.out.println("response ::::::::::" + res);
						System.out.println("httpRespCode ::::::::::"+exchange.getIn().getHeader("CamelHttpResponseCode"));
					}
				}).end();
		

	}

}