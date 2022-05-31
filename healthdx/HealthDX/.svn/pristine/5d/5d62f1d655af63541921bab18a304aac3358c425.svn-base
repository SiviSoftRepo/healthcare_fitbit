package com.sdx.camel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class demoCamel extends RouteBuilder {
 @Override
 public void configure() throws Exception {
	 
	 System.out.println("I am in CAMEL ******************************************");
  from("direct:firstRoute")
   .log("Camel body: ${body}");
 }
}