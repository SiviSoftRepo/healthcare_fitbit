package com.sdx.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelStart {
	
	public static void main(String[] args) throws Exception {
	    CamelContext c=new DefaultCamelContext();       
	    c.addRoutes(new RouteBuilder() {
	        @Override
	        public void configure() throws Exception {          
	            from("direct:start")
	            .process(new Processor() {
	                public void process(Exchange exchange) throws Exception {
	                System.out.println("i am worlds fastest flagship processor ");
	                exchange.getIn().setHeader("CamelHttpMethod", "POST");
	                exchange.getIn().setHeader("Content-Type", "application/json");
	                exchange.getIn().setHeader("username", "servicedx");
	                exchange.getIn().setHeader("password", "sivisoft@123");
	                
	               
	                }
	                })
	              // to the http uri
	                .to("https://dev66250.service-now.com/api/now/table/incident")
	       // to the consumer
	                .to("seda:end");
	        }
	    });



	    c.start();
	    ProducerTemplate pt = c.createProducerTemplate();
	      // for sending request 
	   // pt.send
	   // pt.sendBody("{short_description" :"IOT was device down for more than hour"+"description":"IOT DEVICE DEVICE0001 was down from 07:00 AM"+"impact":"1"+"urgency":"1"+"assignment_group":"Hardware","caller_id":"ServiceDX ServiceDX}");
	    ConsumerTemplate ct = c.createConsumerTemplate();
	    String m = ct.receiveBody("seda:end",String.class);
	    System.out.println(m);
	}

}
