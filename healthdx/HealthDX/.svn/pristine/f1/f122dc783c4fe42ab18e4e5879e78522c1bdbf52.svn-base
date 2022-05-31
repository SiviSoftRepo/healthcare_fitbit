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
import com.sdx.entity.MedicineDevice;
import com.sdx.entity.Nurse;
import com.sdx.platform.config.Memory;
import com.sdx.platform.domain.ResponseDomainUtil;
import com.sdx.service.impl.HomeCareServiceImpl;
import com.sdx.service.impl.MedicineDeviceServiceImpl;
import com.sdx.service.impl.NurseServiceImpl;

import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "nurses")
@Path("/cds/nurses")
public class NurseController {

	@Autowired
	private NurseServiceImpl nurseService; // Injected by Spring

	@GET
	@Path("/get")
	@Produces("application/json")
	public List<BaseEntity> findAllNurses() throws IOException {
		log.info("Get all Nurses:::");
		return nurseService.findAll();
	}

	@GET
	@Path("/getForm")
	@Produces("application/json")
	public String createNurse() throws URISyntaxException, IOException {
		log.info("Create Nurses :::::::::");
		try {
			
			HashMap hash = new HashMap();
			GroovyObject gryObject = Memory.getGroovyObjects().get("NurseExtension");

			String nurseJson = (String) gryObject.invokeMethod("buildFIO", hash);
			return nurseJson;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}

	@POST

	@Path("/add")

	@Consumes("application/json")
	public Response save(LinkedHashMap<String, Object> NurseObject) throws URISyntaxException, IOException {
		log.info("Creating Nurses ::::::::");
		Gson gson = new Gson();
		String NurseJson = gson.toJson(NurseObject, LinkedHashMap.class);
		BaseEntity nurses = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			nurses = mapper.readValue(NurseJson, Nurse.class);
			nurseService.save(nurses);
			System.out.println("Saved IN DB");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(201).build();
	}

	@GET
	@Path("/getNurses/{idifier}")
	@Produces("application/json")
	public Response findById(@PathParam("idifier") String id) throws URISyntaxException {
		log.info("Getting NurseId ::::::::");
		Document document = Document.parse(new Gson().toJson(nurseService.findById(id)));
		if (document == null) {
			return Response.status(404).entity("error").build();
		} else {
			try {
				JSONObject nurseObject = ResponseDomainUtil.buildHomeCareEditDetails(document, "NurseExtension");
				System.out.println("Document : " + document);
				return Response.status(200).entity(nurseObject.toString()).build();
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
	public Response update(@PathParam("id") String id, LinkedHashMap<String, Object> nurseObject)
			throws URISyntaxException {
		log.info("Updating a Nurse ::::::::");
		Gson gson = new Gson();
		String nurseJson = gson.toJson(nurseObject, LinkedHashMap.class);
		BaseEntity nurse = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			nurse = mapper.readValue(nurseJson, Nurse.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		nurseService.update(id, nurse);
		return Response.status(200).build();
	}

	@DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id) throws URISyntaxException {
		log.info("Deleting a Nurse");
		if (nurseService.findById(id) != null) {
			nurseService.delete(id);
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}

}
