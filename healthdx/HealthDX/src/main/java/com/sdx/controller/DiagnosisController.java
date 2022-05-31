package com.sdx.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.sdx.entity.Diagnosis;
import com.sdx.entity.HomeCare;
import com.sdx.entity.Vitals;
import com.sdx.platform.config.Memory;
import com.sdx.platform.domain.ResponseDomainUtil;
import com.sdx.service.impl.DiagnosisServiceImpl;
import com.sdx.service.impl.VitalsServiceImpl;

import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "diagnosis")
@Path("/cds/diagnosis")
public class DiagnosisController {

	@Autowired
	private DiagnosisServiceImpl diagnosisService;
	
	@Autowired
	private VitalsServiceImpl vitalService;

	@GET
	@Path("/get")
	@Produces("application/json")
	public List<BaseEntity> findAllDiagnosis() throws IOException {
		log.info("Get all Diagnosis:::");
		return diagnosisService.findAll();
	}

	@GET
	@Path("/getForm")
	@Produces("application/json")
	public String createHomeCare() throws URISyntaxException, IOException {
		log.info("Create Diagnosis :::::::::");
		try {
			
			ArrayList<Object> vitalsArray = vitalService.findAllDiseaseName(); 
			System.out.println("DiseaseName: " + vitalsArray);
			HashMap<String, ArrayList<Object>> dbrefMap = new HashMap<String, ArrayList<Object>>();
			dbrefMap.put("thresold", 	vitalsArray);

			GroovyObject gryObject = Memory.getGroovyObjects().get("DiagnosisExtension");
			String homeCareJson = (String) gryObject.invokeMethod("buildFIO", dbrefMap);
			return homeCareJson;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}

	@POST
	@Path("/add")
	@Consumes("application/json")
	public Response save(LinkedHashMap<String, Object> diagnosisObject) throws URISyntaxException, IOException {
		log.info("Creating diagnosis ::::::::");
		Gson gson = new Gson();
		String diagnosisJson = gson.toJson(diagnosisObject, LinkedHashMap.class);
		BaseEntity diagnosis = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			diagnosis = mapper.readValue(diagnosisJson, Diagnosis.class);
			diagnosisService.save(diagnosis);
			System.out.println("Saved IN DB");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(201).build();
	}

	@GET
	@Path("/getdiagnosis/{idifier}")
	@Produces("application/json")
	public Response findById(@PathParam("idifier") String id) throws URISyntaxException {
		log.info("Getting DiagnosisID ::::::::");
		System.out.println("FindById Method is here::::");
		Document document = Document.parse(new Gson().toJson(diagnosisService.findById(id)));
		Diagnosis  diagnosis = (Diagnosis) diagnosisService.findById(id);
		String defaultName = diagnosis.getThresold().getName();
		
		ArrayList<Object> vitalsArray = vitalService.findAllDiseaseName();
		
		HashMap diagnosisMap = new HashMap();
		diagnosisMap.put("defaultName", defaultName);
		diagnosisMap.put("thresold", 	vitalsArray);
		
		if (document == null) {
			return Response.status(404).entity("error").build();
		} else {
			try {
				JSONObject diagnosisObject = ResponseDomainUtil.buildDiagnosisEditDetails(document, "DiagnosisExtension", diagnosisMap);
				System.out.println("Document : " + document);
				return Response.status(200).entity(diagnosisObject.toString()).build();
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
	public Response update(@PathParam("id") String id, LinkedHashMap<String, Object> diagnosisObject)
			throws URISyntaxException {
		System.out.println("Upate Diagnosis Method Is Here:::::"+id);
		log.info("Updating a Diagnosis ::::::::"+diagnosisObject);
		String thersold = (String) diagnosisObject.get("thresold");
		 
		Gson gson = new Gson();
		
		BaseEntity diagnosis = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			diagnosisObject.put("thresold", new Vitals(vitalService.findByDiseaseName(thersold).toString()));
			String diagnosisJson = gson.toJson(diagnosisObject, LinkedHashMap.class);
			diagnosis = mapper.readValue(diagnosisJson, Diagnosis.class);
			System.out.println("Diagnosis:::: " + diagnosis);
		} catch (Exception e) {

			e.printStackTrace();
		}
		diagnosisService.update(id, diagnosis);
		return Response.status(200).build();
	}

	@DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id) throws URISyntaxException {
		log.info("Deleting a diagnosis");
		if (diagnosisService.findById(id) != null) {
			diagnosisService.delete(id);
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
	
	@POST
	@Path("/addRef")
	@Consumes("application/json")
	public BaseEntity saveByRef(LinkedHashMap<String, Object> diagnosisObject) {
		log.info("Creating DiagnosisObject ::::::::" + diagnosisObject);
		String thresold = (String) diagnosisObject.get("thresold");
		
		
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("ID Vitals  " + thresold);
		
		try {
			diagnosisObject.put("thresold", new Vitals(vitalService.findByDiseaseName(thresold).toString()));
			
		
			System.out.println("to string val ::"+diagnosisObject);
			
			String diagnosisJson = gson.toJson(diagnosisObject, LinkedHashMap.class);
			System.out.println(diagnosisJson);
			Diagnosis diagnosis = mapper.readValue(diagnosisJson, Diagnosis.class);
			System.out.println("In Controller"+diagnosis);
			diagnosisService.saveByRef(diagnosis);
			System.out.println("Saved in Db");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
