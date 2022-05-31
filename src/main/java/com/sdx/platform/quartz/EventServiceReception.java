package com.sdx.platform.quartz;

import static com.sdx.platform.util.EPLCompileUtil.compileDeploy;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.common.client.fireandforget.EPFireAndForgetPreparedQuery;
import com.espertech.esper.common.client.fireandforget.EPFireAndForgetQueryResult;
import com.espertech.esper.common.client.json.minimaljson.Json;
import com.espertech.esper.common.client.json.minimaljson.JsonObject;
import com.espertech.esper.common.internal.event.map.MapEventBean;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sdx.entity.BaseEntity;
import com.sdx.entity.Diagnosis;
import com.sdx.entity.Vitals;
import com.sdx.platform.config.Memory;

@Path("/eRecept")
public class EventServiceReception {
	
	@GET
	@Path("/test")
	@Produces("application/json")
	public Response testTheWindow() throws URISyntaxException {
		Map<String, Object> status = DefaultActions.testWindow();
		return Response.status(200).entity(status).build();
	}
	
	@POST
	@Path("/ffQuery")
	@Consumes("application/json")
	@Produces("application/json")
	public Response executeFFQuery(String requestSTR) throws URISyntaxException {
		
		String queryMode = "ff";
		
		LinkedHashMap<String, Object> responseMap = new LinkedHashMap<String, Object>();
		try {
			String pQuery = "select * from SensorEventWindow where ((cast(userID?, string) like '%Patient-A%'))";
			/*
			 * System.out.println("pQuery "+pQuery);
			 */
			if (StringUtils.isNotBlank(requestSTR)) {
				JsonObject jsonObject = Json.parse(requestSTR).asObject();
				if (jsonObject.contains("ffQuery")) {
					queryMode = "ff";
					String tempQuery = jsonObject.get("ffQuery").toString();
					System.out.println(tempQuery);
					if (StringUtils.isNotBlank(tempQuery)) {
						pQuery = StringUtils.replaceAll(tempQuery, "\"", "");
						System.out.println("pQuery updated from Request "+pQuery);
					}
				} else {
					queryMode = "other";
					String tempQuery = jsonObject.get("Query").toString();
					if (StringUtils.isNotBlank(tempQuery)) {
						pQuery = StringUtils.replaceAll(tempQuery, "\"", "");
						System.out.println("pQuery updated from Request "+pQuery);
					}
				}
			}
            
			
	        if (StringUtils.equals(queryMode, "ff")) {

				
				Configuration configuration = Memory.getCEP_ENGINE().getConfigurationDeepCopy();
				CompilerArguments args = new CompilerArguments(configuration);
	            args.getPath().add(Memory.getCEP_ENGINE().getRuntimePath());
	            
	        	EPCompiled onDemandQueryCompiled;
		        try {
		            onDemandQueryCompiled = EPCompilerProvider.getCompiler().compileQuery(pQuery, args);
		            
		        } catch (EPCompileException e) {
		            throw new RuntimeException(e);
		        }
	        	EPFireAndForgetPreparedQuery onDemandQuery = Memory.getCEP_ENGINE().getFireAndForgetService().prepareQuery(onDemandQueryCompiled);
				EPFireAndForgetQueryResult result = onDemandQuery.execute();
				int resultcounter = 0;
				for (Iterator<EventBean> it = result.iterator(); it.hasNext(); ) {
					resultcounter++;
		            EventBean eventBean = it.next();
		        	System.out.println("\nRESULT, Here "+eventBean+" -> ["+eventBean.getUnderlying().getClass()+"] "+eventBean.getUnderlying());
		        	
					if (eventBean instanceof MapEventBean) {
						MapEventBean meb = (MapEventBean) eventBean;
						System.out.println("RESULT keys :::: " + meb.getProperties().keySet());
						if (meb.getProperties() != null) {
							for (String key : meb.getProperties().keySet()) {
								try {
									System.out.println("\nBY " + key + ", Properties() [" + meb.getProperties().containsKey(key) + "]");
									System.out.println("-----------------------------------------------------------------------------");
									System.out.println("\t" + key + "  -> " + meb.getProperties().get(key));
									responseMap.put("Result" + resultcounter, meb);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
		        }
				System.out.println("result "+result.getArray().length);
	        } 
	        
	        else {
	        	
	    		EPDeployment epDep = compileDeploy(Memory.getCEP_ENGINE(), pQuery);
	    		EPStatement stmt = epDep.getStatements()[0];
	    		int resultcounter = 0;
				/*for (Iterator<EventBean> it = stmt.iterator(); it.hasNext(); ) {
					resultcounter++;
		            EventBean eventBean = it.next();
		        	System.out.println("\nRESULT, Here "+eventBean+" -> ["+eventBean.getUnderlying().getClass()+"] "+eventBean.getUnderlying());
		        	
					if (eventBean instanceof MapEventBean) {
						MapEventBean meb = (MapEventBean) eventBean;
						System.out.println("RESULT keys :::: " + meb.getProperties().keySet());
						if (meb.getProperties() != null) {
							for (String key : meb.getProperties().keySet()) {
								try {
									System.out.println("\nBY " + key + ", Properties() [" + meb.getProperties().containsKey(key) + "]");
									System.out.println("-----------------------------------------------------------------------------");
									System.out.println("\t" + key + "  -> " + meb.getProperties().get(key));
									responseMap.put("Result" + resultcounter, meb);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
		        }*/
				
				stmt.addListener(new UpdateListener() {
					@Override
					public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
						if (newEvents !=null) {
							System.out.println("@ STATEMENT newEvents ");
							for (EventBean eventBean: newEvents) {
								System.out.println("\tEB : "+eventBean.getUnderlying());
								if (eventBean instanceof MapEventBean) {
									MapEventBean meb = (MapEventBean) eventBean;
									
									for (Entry<String, Object> es: meb.getProperties().entrySet()) {
										if (es.getValue()!=null && es.getValue() instanceof Map) {
											HashMap<String, Object> outFinal = (HashMap<String, Object>) es.getValue();
											for (Entry<String, Object> ofES : outFinal.entrySet()) {
												if (ofES.getValue()!=null && ofES.getValue() instanceof MapEventBean) {
													MapEventBean valuebean = (MapEventBean) ofES.getValue();
													System.out.println("valuebean "+valuebean.getUnderlying());
												}
											}
										}
									}
									/*System.out.println("RESULT keys :::: " + meb.getProperties().keySet());
									
									 * if (meb.getProperties() != null) { for (String key :
									 * meb.getProperties().keySet()) { try { System.out.println("\nBY " + key +
									 * ", Properties() [" + meb.getProperties().containsKey(key) + "]");
									 * System.out.println(
									 * "-----------------------------------------------------------------------------"
									 * ); System.out.println("\t" + key +
									 * " DT ("+meb.getProperties().get(key).getClass()+") -> " +
									 * meb.getProperties().get(key)); responseMap.put("Result" + resultcounter,
									 * meb); } catch (Exception e) { e.printStackTrace(); } } }
									 */
								}
							}
						}
					}
				});
				
	    		System.out.println("DONE the statement Execution");
	        }
	        
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(responseMap).build();
	}
	
	@POST
	@Path("/publish")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(String pEventString) throws URISyntaxException {
		
		try {
			JsonObject jsonObject = Json.parse(pEventString).asObject();
			if (jsonObject!=null) {
				if (!jsonObject.contains("deviceID")) {
					jsonObject.add("deviceID", "fitbit-124578");
				}
				if (!jsonObject.contains("userID")) {
					jsonObject.add("userID", "Patient-A");
				}
				//System.out.println("\nJsonObject "+jsonObject.toString());

				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
				Map<String, Object> out = mapper.readValue(jsonObject.toString(), new TypeReference<Map<String, Object>>() { });
				Map<String, Object> mapOne = Collections.singletonMap("item", out);
				
				//For logging
				System.out.println("\nMapped Event SENT: "+jsonObject.toString());

				//System.out.println("\npEventString "+pEventString.toString());
				Memory.getCEP_ENGINE().getEventService().sendEventMap(mapOne, "SensorEvent");
				//Memory.getCEP_ENGINE().getEventService().sendEventJson(jsonObject.toString(), "SensorEvent");
				//Memory.getCEP_ENGINE().getEventService().sendEventJson(jsonObject.toString(), "SensorEvent");
				
			} else { return Response.status(400).entity("{Invalid format}").build(); }
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(200).build();
	}

}
