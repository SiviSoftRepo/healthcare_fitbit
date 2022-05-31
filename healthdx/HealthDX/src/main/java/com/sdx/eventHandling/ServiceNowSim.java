package com.sdx.eventHandling;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.impl.DefaultCamelContext;

import org.json.JSONObject;

public class ServiceNowSim {

	public static CamelContext camelContext = null;
	public static ProducerTemplate producerTemplate = null;

	static {
		System.out.println(" Camel context invoke :::::::::::::::::");
		camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new SedaEventPush());
			HttpComponent httpComponent = new HttpComponent();
			camelContext.addComponent("https", httpComponent);
			camelContext.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		// PropertyConfigurator.configure("C:/Users/SONY/Desktop/camel-seda/src/main/resources/log4j.properties");
		camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new SedaEventPush());
		HttpComponent httpComponent = new HttpComponent();
		camelContext.addComponent("https", httpComponent);
		camelContext.start();

		producerTemplate = camelContext.createProducerTemplate();
		producerTemplate.sendBody(SedaEventPush.SEDA_START_ROUTE, "");

		Thread.sleep(2000);
	}

	public static void buildEvent(JSONObject eventPayLoad) throws Exception {

		producerTemplate = camelContext.createProducerTemplate();
		// producerTemplate.sendBody(SedaEndPointRouter.SEDA_START_ROUTE, eventPayLoad);

		producerTemplate.requestBody(SedaEventPush.SEDA_START_ROUTE, eventPayLoad);

		// Thread.sleep(2000);
	}
}