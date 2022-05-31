package com.sdx.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sdx.entity.BaseEntity;
import com.sdx.entity.HomeCare;
import com.sdx.entity.Vitals;
import com.sdx.platform.config.Memory;
import com.sdx.platform.domain.ResponseDomainUtil;
import com.sdx.service.impl.HomeCareServiceImpl;
import com.sdx.service.impl.VitalsServiceImpl;

import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "vitals")
@Path("/cds/vitals")
public class VitalsController {

	@Autowired
	private VitalsServiceImpl thersoldService; // Injected by Spring

	@GET
	@Path("/get")
	@Produces("application/json")
	public List<BaseEntity> findAllThersold() throws IOException {
		log.info("Get all HomeCare:::");
		return thersoldService.findAll();
	}

	@GET
	@Path("/getForm")
	@Produces("application/json")
	public String createThersold() throws URISyntaxException, IOException {
		log.info("Create HomeCare :::::::::");
		try {

			HashMap hash = new HashMap();
			GroovyObject gryObject = Memory.getGroovyObjects().get("VitalsExtension");

			String thersoldJson = (String) gryObject.invokeMethod("buildFIO", hash);
			return thersoldJson;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}

	@POST
	@Path("/add")
	@Consumes("application/json")
	public Response save(LinkedHashMap<String, Object> thersoldObject) throws URISyntaxException, IOException {
		log.info("Creating HomeCare ::::::::");
		Gson gson = new Gson();
		String thersoldJson = gson.toJson(thersoldObject, LinkedHashMap.class);
		BaseEntity thersold = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			thersold = mapper.readValue(thersoldJson, Vitals.class);
			thersoldService.save(thersold);
			System.out.println("Saved IN DB");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(201).build();
	}

	@GET
	@Path("/getvitals/{idifier}")
	@Produces("application/json")
	public Response findById(@PathParam("idifier") String id) throws URISyntaxException {
		log.info("Getting HomeCareID ::::::::");
		Document document = Document.parse(new Gson().toJson(thersoldService.findById(id)));
		
		Vitals vitals = (Vitals) thersoldService.findById(id);
		String defaultSuffix = vitals.getSuffix();
		
		HashMap vitaldbref = new HashMap();
		vitaldbref.put("defaultSuffix",defaultSuffix);

		if (document == null) {
			return Response.status(404).entity("error").build();
		} else {
			try {
				JSONObject homeCareObject = ResponseDomainUtil.buildVitalsEditDetails(document, "VitalsExtension", vitaldbref);
				return Response.status(200).entity(homeCareObject.toString()).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return Response.status(404).entity("error").build();

	}

	@POST
	@Path("/update/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(@PathParam("id") ObjectId id, LinkedHashMap<String, Object> thersoldObject)
			throws URISyntaxException {
		log.info("Updating a Thersold ::::::::");
		Gson gson = new Gson();
		String thersoldJson = gson.toJson(thersoldObject, LinkedHashMap.class);
		BaseEntity thersold = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			thersold = mapper.readValue(thersoldJson, Vitals.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		thersoldService.update(id, thersold);
		return Response.status(200).build();
	}

	@DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id) throws URISyntaxException {
		log.info("Deleting a HomeCare");
		if (thersoldService.findById(id) != null) {
			thersoldService.delete(id);
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}

}
