package com.sdx.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectEndPointRouter extends RouteBuilder {

	static Logger LOG = LoggerFactory.getLogger(DirectEndPointRouter.class);

	public static final String DIRECT_END_ROUTE = "direct:end";
	public static final String DIRECT_START_ROUTE = "direct:start";

	@Override
	public void configure() throws Exception {
		from(DIRECT_START_ROUTE).routeId("StartRouteId").setBody().simple("Start Message").to(DIRECT_END_ROUTE)
				.process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						LOG.info("Message at start route completion");
					}
				});

		from(DIRECT_END_ROUTE).routeId("EndRouteId").delay(10000).setBody().simple("End Message")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						LOG.info("message after end-route completion");

					}
				});
	}
}
