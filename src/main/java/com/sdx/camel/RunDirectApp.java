package com.sdx.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;

public class RunDirectApp {

	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("C:/Users/SONY/Desktop/camel-seda/src/main/resources/log4j.properties");
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new DirectEndPointRouter());
		camelContext.start();

		ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
		producerTemplate.sendBody(DirectEndPointRouter.DIRECT_START_ROUTE, "start Message");
	}
}