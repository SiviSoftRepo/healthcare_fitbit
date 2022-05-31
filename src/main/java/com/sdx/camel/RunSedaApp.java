package com.sdx.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

public class RunSedaApp {
	
	public static CamelContext camelContext = null;
	public static ProducerTemplate producerTemplate = null;
	
	static {
		System.out.println(" Camel context invoke :::::::::::::::::");
		camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new SedaEndPointRouter());
			camelContext.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	public static void main(String[] args) throws Exception {
		//PropertyConfigurator.configure("C:/Users/SONY/Desktop/camel-seda/src/main/resources/log4j.properties");
		camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new SedaEndPointRouter());
		 //HttpComponent httpComponent = new HttpComponent();
		// camelContext.addComponent("http4", httpComponent);
		camelContext.start();

	
		producerTemplate = camelContext.createProducerTemplate(); 
		producerTemplate.sendBody(SedaEndPointRouter.SEDA_START_ROUTE, "");
		

		Thread.sleep(2000);
	}
	
	
	public static void buildEvent(JSONObject eventPayLoad) throws Exception{
		
		producerTemplate = camelContext.createProducerTemplate();
		//producerTemplate.sendBody(SedaEndPointRouter.SEDA_START_ROUTE, eventPayLoad);
		producerTemplate.requestBody(SedaEndPointRouter.SEDA_START_ROUTE, eventPayLoad);

	  // Thread.sleep(2000);
	}
}