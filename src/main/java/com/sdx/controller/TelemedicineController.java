
/*
 * Created on 2020-02-08 ( Date ISO 2020-02-08 - Time 20:02:25 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.1.2
 */


package com.sdx.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sdx.entity.BaseEntity;
import com.sdx.entity.Telemedicine;
import com.sdx.service.impl.TelemedicineServiceImpl;

//--- Common classes

import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.bson.types.ObjectId;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.*;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sdx.platform.config.Memory;
import com.sdx.platform.domain.ResponseDomainUtil;
import groovy.lang.GroovyObject;
import com.sdx.service.impl.TelemedicineServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "telemedicines")
@Path("/cds/teleMedicine")
public class TelemedicineController {

	@Autowired
    private TelemedicineServiceImpl telemedicineService; // Injected by Spring

    @GET
	@Path("/get")
	@Produces("application/json")
	public List<BaseEntity> findAllTelemedicine() throws IOException {
		log.info("Get all Telemedicine:::");
		return telemedicineService.findAll();
	}

    @GET
	@Path("/getForm")
	@Produces("application/json")
	public String createTelemedicine() throws URISyntaxException, IOException {
		log.info("Create Telemedicine :::::::::");
		
		
		try {
			
			HashMap has = new HashMap();
			GroovyObject gryObject = Memory.getGroovyObjects().get("TeleMedicineExtension");
			String telemedicineJson = (String) gryObject.invokeMethod("buildFIO", has);
			return telemedicineJson;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}
    
    @POST
	@Path("/add")
	@Consumes("application/json")
	public Response save(LinkedHashMap<String, Object> telemedicineObject) throws URISyntaxException, IOException {
		log.info("Creating Telemedicine ::::::::");
		Gson gson = new Gson();
		String telemedicineJson = gson.toJson(telemedicineObject, LinkedHashMap.class);
		BaseEntity telemedicine = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			telemedicine = mapper.readValue(telemedicineJson, Telemedicine.class);
			telemedicineService.save(telemedicine);
			System.out.println("TeleMedicine SAved");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(201).build();
	}
    
    @GET
	@Path("/getteleMedicine/{idifier}")
	@Produces("application/json")
	public JSONObject findById(@PathParam("idifier") String id) throws URISyntaxException {
		log.info("Getting TelemedicineID ::::::::");
		
		System.out.println("TelemiID "+ id);
		
		
			try {
				Document document = Document.parse(new Gson().toJson(telemedicineService.findById(id)));
			//	JSONObject telemedicineObject = ResponseDomainUtil.buildTelemedicineEditDetails(document);
				JSONObject usersObject = ResponseDomainUtil.buildExtensionDetails(document, "TeleMedicineExtension");
				//return Response.status(200).entity(telemedicineObject.toString()).build();
				return usersObject;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return null;

	}

	/*
	 * @POST
	 * 
	 * @Path("/update/{id}")
	 * 
	 * @Consumes("application/json")
	 * 
	 * @Produces("application/json") public Response update(@PathParam("id") String
	 * id, LinkedHashMap<String, Object> telemedicineObject) throws
	 * URISyntaxException { log.info("Updating a Telemedicine ::::::::"); Gson gson
	 * = new Gson(); String telemedicineJson = gson.toJson(telemedicineObject,
	 * LinkedHashMap.class); BaseEntity telemedicine = null; try { ObjectMapper
	 * mapper = new ObjectMapper(); telemedicine =
	 * mapper.readValue(telemedicineJson, Telemedicine.class); } catch (Exception e)
	 * {
	 * 
	 * e.printStackTrace(); } telemedicineService.update(id, telemedicine); return
	 * Response.status(200).build(); }
	 */

    @DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id) throws URISyntaxException {
		log.info("Deleting a Telemedicine");
		if (telemedicineService.findById(id) != null) {
			telemedicineService.delete(id);
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}

}


	
        	