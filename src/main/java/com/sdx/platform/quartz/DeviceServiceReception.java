package com.sdx.platform.quartz;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;

import com.sdx.platform.config.Memory;

@Path("/device")
public class DeviceServiceReception {
	
	private String JSON_SUCCESS = "{\"status\":\"success\"}";
	private String JSON_FAILURE = "{\"status\":\"failure\"}";
	
	private static SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-mm-dd");
	
	static {
		sdfrmt.setLenient(false);
	}
    
	
	@GET
	@Path("/pull/{deviceID}/{dateStr}")
	@Produces("application/json")
	public Response pullDeviceData(@PathParam("deviceID") String id, @PathParam("dateStr") String dateStr) throws URISyntaxException {
		try {
			dateStr = StringUtils.trim(dateStr);
			if (StringUtils.isBlank(dateStr)) {
				dateStr = "today";
			} else {
				if (!StringUtils.equals(dateStr, "today")) {
					try {
				        sdfrmt.parse(dateStr); 
				        System.out.println(dateStr+" is valid date format");
				    } catch (Exception e) {
				        System.out.println(dateStr+" is Invalid Date format");
						return Response.status(500).entity("{\"status\":\"failure - invalid date format\"}").build(); 
				    }
				}
			}
			
			ProducerTemplate template = Memory.getGLOBAL_CONTEXT().createProducerTemplate();
			System.out.println("TEMPLATE beean created");
			LinkedHashMap<String, Object> hdrs = new LinkedHashMap<String, Object>();
			hdrs.put("deviceID", id);
			hdrs.put("dateStr", dateStr);
			
			template.requestBodyAndHeaders("direct:datatpull", "HCDevice", hdrs);
			template.stop();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(JSON_FAILURE).build(); 
		}
		return Response.status(200).entity(JSON_SUCCESS).build(); 
	}

}
