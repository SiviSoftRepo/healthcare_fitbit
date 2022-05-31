package com.sdx.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

@Path("/cds/diagnosis")
public class EventReception {
	
	@POST
	@Path("/postEvent")
	@Consumes("application/json")
	public Response postEvent(String pEvent) throws URISyntaxException, IOException {
		
		System.out.println("Event Attribute "+pEvent);
		if (StringUtils.isNotBlank(pEvent)) {
			
			Object eventJSON = JSON.parse(pEvent);
			System.out.println("eventJSON "+eventJSON);
			
			//Memory.getCEP_ENGINE().
			
			return Response.status(200).entity("Event accepted ").build();
		}
		
		return Response.status(400).entity("BAD Request").build();
		
	}

}
