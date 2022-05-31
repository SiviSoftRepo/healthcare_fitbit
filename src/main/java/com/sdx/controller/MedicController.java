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
import com.sdx.entity.Medic;
import com.sdx.platform.config.Memory;
import com.sdx.platform.domain.ResponseDomainUtil;
import com.sdx.service.impl.HomeCareServiceImpl;
import com.sdx.service.impl.MedicServiceImpl;

import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "medics")
@Path("/cds/userss")
public class MedicController {

	@Autowired
	private MedicServiceImpl medicService; // Injected by Spring

	@GET
	@Path("/get")
	@Produces("application/json")
	public List<BaseEntity> findAllMedic() throws IOException {
		log.info("Get all HomeCare:::");
		return medicService.findAll();
	}

	@GET
	@Path("/getForm")
	@Produces("application/json")
	public String createMedic() throws URISyntaxException, IOException {
		log.info("Create HomeCare :::::::::");
		try {

			HashMap hash = new HashMap();
			GroovyObject gryObject = Memory.getGroovyObjects().get("MedicExtension");

			String medicJson = (String) gryObject.invokeMethod("buildFIO", hash);
			return medicJson;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}

	@POST
	@Path("/add")
	@Consumes("application/json")
	public Response save(LinkedHashMap<String, Object> medicObject) throws URISyntaxException, IOException {
		log.info("Creating HomeCare ::::::::");
		Gson gson = new Gson();
		String medicJson = gson.toJson(medicObject, LinkedHashMap.class);
		BaseEntity medics = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			medics = mapper.readValue(medicJson, Medic.class);
			medicService.save(medics);
			System.out.println("Saved IN DB");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(201).build();
	}

	@GET
	@Path("/getusers/{idifier}")
	@Produces("application/json")
	public Response findById(@PathParam("idifier") String id) throws URISyntaxException {
		log.info("Getting HomeCareID ::::::::");
		Document document = Document.parse(new Gson().toJson(medicService.findById(id)));
		if (document == null) {
			return Response.status(404).entity("error").build();
		} else {
			try {
				JSONObject medicObject = ResponseDomainUtil.buildHomeCareEditDetails(document, "MedicExtension");
				System.out.println("Document : " + document);
				return Response.status(200).entity(medicObject.toString()).build();
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
	public Response update(@PathParam("id") ObjectId id, LinkedHashMap<String, Object> medicObject)
			throws URISyntaxException {
		log.info("Updating a HomeCare ::::::::");
		Gson gson = new Gson();
		String medicJson = gson.toJson(medicObject, LinkedHashMap.class);
		BaseEntity medic = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			medic = mapper.readValue(medicJson, Medic.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		medicService.update(id, medic);
		return Response.status(200).build();
	}

	@DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id) throws URISyntaxException {
		log.info("Deleting a HomeCare");
		if (medicService.findById(id) != null) {
			medicService.delete(id);
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}

}
