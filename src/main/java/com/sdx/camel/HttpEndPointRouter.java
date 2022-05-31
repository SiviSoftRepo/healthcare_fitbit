package com.sdx.camel;

import javax.servlet.http.HttpServletResponse;

//import javax.servlet.http.HttpServletResponse;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.HttpRequest;
//import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpEndPointRouter extends RouteBuilder {

	static Logger LOG = LoggerFactory.getLogger(SedaEndPointRouter.class);

	public static final String DIRECT_START_ROUTE = "direct:start";
	public static final String SEDA_END_ROUTE = "seda:end"; 

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
	public void configure() throws Exception{
		String test =
				
				  "{ \"short_description\": \"IOT was device down for more than hour\",\r\n" + "  \"description\": \"IOT DEVICE DEVICE0001 was down from 07:00 AM\",\r\n"+
				  "  \"impact\": \"1\"\r\n" +
				  "  \"urgency\": 1\r\n" + "\"  \"assignment_group\": \"Hardware\"\r\n"+ "\"caller_id\": \"  ServiceDX ServiceDX\"\r\n"+"}";

		   
		
		
		
		from("direct:start")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				Object inVal = exchange.getIn().getBody();
			System.out.println("Process started"+inVal);
			exchange.getIn().setBody(inVal);
			}
			
		})
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
	    .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
	    .setHeader(Exchange.HTTP_QUERY, constant("userId=123&userName=cAMEL"))
	    //.delay(10000) 
	   // .setBody().constant(test)
	    .convertBodyTo(String.class)
	    .toD("https://dev66250.service-now.com/api/now/table/incident")
	     .process(new Processor() {
	    	 public void process(Exchange exchange) throws Exception { // TODO
	    		
	    		 HttpRequest response = (HttpRequest) exchange.getIn().getBody(HttpServletResponse.class);
	    		  System.out.println("response ::::::::::"+response);
	    }
	     });
		
	
		
	}
}