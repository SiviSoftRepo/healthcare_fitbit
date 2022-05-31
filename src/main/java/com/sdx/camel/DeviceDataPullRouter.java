package com.sdx.camel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.util.toolbox.FlexibleAggregationStrategy;
import org.apache.camel.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdx.platform.config.Memory;

public class DeviceDataPullRouter extends RouteBuilder   {
	
	static Logger LOG = LoggerFactory.getLogger(DirectEndPointRouter.class);

	public static final String DIRECT_START_ROUTE = "direct:DataPullStart";
	public static final String DIRECT_END_ROUTE = "direct:DataPullEnd";
	
	private static final String SOURCE_APP		= "";
	private static final String TARGET_APP		= "http://localhost:10101/healthDX/eRecept/publish";
	
	private static final String[] TRACKER_ACTIVITIES_ARRAY = new String[] {"devices","calories","steps","distance","floors","elevation", "minutesSedentary",
															"minutesLightlyActive","minutesFairlyActive","minutesVeryActive","activityCalories"};
	 

	@Override
	public void configure() throws Exception {
		
		from("direct:invokePayload")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				//System.out.println("PUBLISH MSG this "+exchange.getIn().getBody());
				exchange.getIn().setBody(invokeSerialization(exchange.getIn().getBody(Map.class)));
			}
		})
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.to("http4://localhost:10101/healthDX/eRecept/publish")
		.end();
		
		
		from("direct:datatpull")
		.log("VETTING request for device")
		.description("Data hunt from DEVICES").id("datapull-api")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				System.out.println("GIVEN HEADERS::::::::::: "+exchange.getIn().getHeaders());
				StepHttp steps = null;
				CaloriesHttp calories = new CaloriesHttp();
				LinkedHashMap<String, Object> responses = null;
				steps.sendPOST(responses);
				calories.sendPOST(responses);
			}
		})
		.multicast(new AggregationStrategy() {
			public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
				ProducerTemplate tmplt = newExchange.getContext().createProducerTemplate();
				tmplt.sendBody("direct:invokePayload", newExchange.getIn().getBody());
				try {
					tmplt.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("invoking newExchange ["+newExchange.getIn().getBody().getClass()+"] "+newExchange.getIn().getBody());
				System.out.println("????????????????????????Hearttttt"+newExchange.getIn().getBody());
				
				HeartHttp heartRate = new HeartHttp();
				try {
					
					heartRate.sendPOST((HashMap) newExchange.getIn().getBody());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return oldExchange;
				
				/*LinkedHashMap<String, Object> responses = null;
				if (oldExchange == null) {
					responses = new LinkedHashMap<String, Object>();
					responses.put((String) newExchange.getIn().getHeader("responseKey"), 
							invokeDeserialization((String) newExchange.getIn().getBody(String.class)));
					newExchange.getIn().setBody(responses);
		            return newExchange;
		        } else {
		        	responses = oldExchange.getIn().getBody(LinkedHashMap.class);
					responses.put((String) newExchange.getIn().getHeader("responseKey"), 
							invokeDeserialization((String) newExchange.getIn().getBody(String.class)));
		        	newExchange.getIn().setBody(responses);
		        }
				return oldExchange; */
			}
		})
		.stopOnException()
		.parallelAggregate()
		.parallelProcessing()
		//.to("direct:Sleep")
		//.to("direct:SleepGoal")
		//.to("direct:fwb")
		.to("direct:ActivitySummary")
		.to("direct:ActivitySeries")
		//.to("direct:HeartRateSeries")
		.end()
		;
		
		
		from("direct:Sleep").routeId("SLEEP")
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
		.setHeader("Authorization", constant(Memory.getDEVICE_AUTH_CODES().get("fitbitSivi")))
		.log("Sleep HEADERS>>>>>> ${in.headers}")
		.toD("https4://api.fitbit.com/1.2/user/-/sleep/date/${header.dateStr}.json")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().getHeaders().put("responseKey", "sleep_data");
				exchange.getIn().setBody(invokeDeserialization(exchange.getIn().getBody(String.class)));
			}
		})
		.end();
		
		
		
		
		
		from("direct:SleepGoal").routeId("SLEEP-GOAL")
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
		.setHeader("Authorization", constant(Memory.getDEVICE_AUTH_CODES().get("fitbitSivi")))
		.to("https4://api.fitbit.com/1/user/-/sleep/goal.json")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().getHeaders().put("responseKey", "sleep_goal");
				exchange.getIn().setBody(invokeDeserialization(exchange.getIn().getBody(String.class)));
				System.out.println("SLEEEEEEEEEEEEEEEEEEEEPPPPPPPPPPPPPPPPPPPPPEEEEEEEEEEEEEEEEEEEP");
			}
		})
		.end();
		
		
		from("direct:ActivitySummary").routeId("ACT-SUMMARY")
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
		.setHeader("Authorization", constant(Memory.getDEVICE_AUTH_CODES().get("fitbitSivi")))
		.toD("https://api.fitbit.com/1/user/-/activities/date/${header.dateStr}.json")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().getHeaders().put("responseKey", "activity_summary");
				exchange.getIn().setBody(invokeDeserialization(exchange.getIn().getBody(String.class)));
				System.out.println("////////////////ssssssssssssss22222222222222222222222222222222");
			}
		})
		.end();
		

		from("direct:ActivitySeries").routeId("ACTIVITIES")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setBody(TRACKER_ACTIVITIES_ARRAY);
				System.out.println("////////////////d");
			}
		})
		.split(body(), new AggregationStrategy() {
			public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
				LinkedHashMap<String, Object> responses = null;
				if (oldExchange == null) {
					responses = new LinkedHashMap<String,Object>();
		        	responses.putAll(invokeDeserialization((String) newExchange.getIn().getBody(String.class)));
					newExchange.getIn().setBody(responses);
		            return newExchange;
		        } else {
		        	responses = oldExchange.getIn().getBody(LinkedHashMap.class);
		        	responses.putAll(invokeDeserialization((String) newExchange.getIn().getBody(String.class)));
		        	newExchange.getIn().setBody(responses);
		        
		        }
				System.out.println("////////////////ssssssssssssss"+responses);
				StepHttp steps = new StepHttp();
				
				System.out.println("11255555555555");
				
				return oldExchange;
			}
		})
		//.stopOnException()
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().getHeaders().put("responseKey", "tracker_"+exchange.getIn().getBody(String.class));
			}
		})
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
		.setHeader("Authorization", constant(Memory.getDEVICE_AUTH_CODES().get("fitbitSivi")))
		.toD("https4://api.fitbit.com/1/user/-/activities/tracker/${body}/date/${header.dateStr}/1d.json")
		.end()
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				/* System.out.println("INVOKED ALL SUB tracker Events ::::::::::::: " +
				* " ["+exchange.getIn().getBody().getClass()+"] "+exchange.getIn().getBody());
				
				String response  = invokeSerialization((Map<String, Object>) exchange.getIn().getBody());
				System.out.println("\t INVOKED tracket acts : "+response);*/
			}
		});
		

		from("direct:HeartRateSeries").routeId("HRS")
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
		.setHeader("Authorization", constant(Memory.getDEVICE_AUTH_CODES().get("fitbitSivi")))
		.toD("https4://api.fitbit.com/1/user/-/activities/heart/date/${header.dateStr}/1d/1min.json")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().getHeaders().put("responseKey", "heartRate");
				exchange.getIn().setBody(invokeDeserialization(exchange.getIn().getBody(String.class)));
				System.out.println("Heart::::::::::::::::::::::::::::::::::::**********"+exchange);
			}
		})
		.end();
		

		from("direct:fwb").routeId("FWB")
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
		.setHeader("Authorization", constant(Memory.getDEVICE_AUTH_CODES().get("fitbitSivi")))
		.toD("https4://api.fitbit.com/1/user/-/body/fat/date/${header.dateStr}/1d.json")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().getHeaders().put("responseKey", "fat_data");
				exchange.getIn().setBody(invokeDeserialization(exchange.getIn().getBody(String.class)));
			}
		})
		.end();

		
	}

	protected static Map<String, Object> invokeDeserialization(String body) {
		ObjectMapper mapper = new ObjectMapper();
		//mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		try {
			Map<String, Object> out = mapper.readValue(body, new TypeReference<Map<String, Object>>() { });
			return out;
		} catch (JsonProcessingException e) {
			System.out.println("ERROR on \n"+body);
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}

	protected static String invokeSerialization(Map<String, Object> body) {
		ObjectMapper mapper = new ObjectMapper();
		//mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		try {
			return mapper.writeValueAsString(body);
		} catch (JsonProcessingException e) {
			System.out.println("ERROR on \n"+body);
			e.printStackTrace();
		}
		return "{'error':'serialization-failure'}";
	}
}
