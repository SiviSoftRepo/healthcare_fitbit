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
import com.sdx.CommonUtils;
import com.sdx.entity.BaseEntity;
import com.sdx.entity.Diagnosis;
import com.sdx.entity.EventDistributor;
import com.sdx.entity.HomeCare;
import com.sdx.entity.Nurse;
import com.sdx.entity.Patient;

import com.sdx.entity.*;
import com.sdx.service.impl.*;
import com.sdx.eventHandling.HealthCareEventGen;
import com.sdx.fitbit.FitbitApi20Example;
import com.sdx.platform.config.Memory;
import com.sdx.platform.domain.ResponseDomainUtil;
import com.sdx.service.impl.DiagnosisServiceImpl;
import com.sdx.service.impl.EventDistributorServiceImpl;
import com.sdx.service.impl.PatientServiceImpl;

import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "eventDistributor")
@Path("/cds/event")
public class EventDistributorController {

	@Autowired
	private DiagnosisServiceImpl diagnosisService;

	@Autowired
	private VitalsServiceImpl thersoldService;

	@Autowired
	private PatientServiceImpl patientService;

	@Autowired
	private EventDistributorServiceImpl eventDistributorServiceImpl;
	
	@Autowired
	private FitbitApi20Example fitbitexample;

	private Vitals vitals;

	private Diagnosis diagnosis;

	private Patient patientEntity;

	@GET
	@Path("/getEventForm")
	@Produces("application/json")
	public String createEventForm() throws URISyntaxException, IOException {
		log.info("Create EventDistr :::::::::");
		HashMap hash = new HashMap();
		try {
			
			System.out.println("FitBit Values"+fitbitexample.getValue());
			ArrayList<Object> patientList = patientService.findAllPatientName();
			ArrayList<Object> diagoniseList = diagnosisService.findAllDiagnosis();
			ArrayList<Object> vitalsList = thersoldService.findAllVitals();

			hash.put("patientList", patientList);
			hash.put("diagoniseList", diagoniseList);
			hash.put("vitalList", vitalsList);
			GroovyObject gryObject = Memory.getGroovyObjects().get("EventDistributorExtension");
			String homeCareJson = (String) gryObject.invokeMethod("buildFIO", hash);
			return homeCareJson;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}

	@POST
	@Path("/getVitals")
	@Produces("application/json")
	public Response getVitalDetails(LinkedHashMap<String, Object> eventObject) throws URISyntaxException, IOException {
		log.info("Get vital Values :::::::::" + eventObject);
		HashMap hash = new HashMap();
		try {
			String vitalName = (String) eventObject.get("vitalName");

			if (vitalName.length() > 0) {
				vitals = thersoldService.findByDiseaseName(vitalName);
				ArrayList<Object> patientList = patientService.findAllPatientName();
				ArrayList<Object> diagoniseList = diagnosisService.findAllDiagnosis();
				ArrayList<Object> vitalsList = thersoldService.findAllVitals();
				String patient = (String) eventObject.get("patientName");
				String diagnosisName = (String) eventObject.get("diagnosisName");
				String vital = (String) eventObject.get("vitalName");
				String testResult = (String) eventObject.get("testResult");
				System.out.println("testResult::::::" + testResult);
				hash.put("patientList", patientList);
				hash.put("diagoniseList", diagoniseList);
				hash.put("vitalList", vitalsList);
				hash.put("vitalValue", "Vital valuse ranges from  " + vitals.getLowerLimit() + vitals.getSuffix()
						+ " - " + vitals.getUpperLimit() + vitals.getSuffix());
				hash.put("patient", patient);
				hash.put("diagnosisName", diagnosisName);
				hash.put("vital", vital);
				hash.put("testResult", testResult);

				String eventDistGroovyObject = ResponseDomainUtil.buildEventDis("EventDistributorExtension", hash);
				return Response.status(200).entity(eventDistGroovyObject.toString()).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@POST
	@Path("/testResultCalc")
	@Consumes("application/json")
	public Response testResultCalculation(LinkedHashMap<String, Object> eventObject)
			throws URISyntaxException, IOException {
		log.info("test calcula EventDistr :::::::::" + eventObject);
		HashMap eventHash = new HashMap();
		String patient = (String) eventObject.get("patientName");
		String diagnosisName = (String) eventObject.get("diagnosisName");
		String vital = (String) eventObject.get("vitalName");
		String testResult = (String) eventObject.get("testResult");
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("ID patient  " + patient);
		System.out.println("ID DIAGNOSIS  " + diagnosisName);
		System.out.println("ID vital  " + vital);
		try {
			vitals = thersoldService.findByDiseaseName(vital);
			patientEntity = patientService.findByPatientName(patient);
			System.out.println("patientEntity :::::::::" + patientEntity.getLocation());
			System.out.println("to string val ::" + vitals.getLowerLimit() + vitals.getUpperLimit());
			diagnosis = diagnosisService.findByDiagnosisName(diagnosisName);

			Boolean resultStatus = CommonUtils.checkRangeLimit(Integer.parseInt(vitals.getLowerLimit()),
					Integer.parseInt(vitals.getUpperLimit()), Integer.parseInt(testResult));
			System.out.println("resultStatus :::::::::" + resultStatus);
			if (resultStatus) {
				System.out.println(" Trigger Event");
				HealthCareEventGen.buildPatientDiagoniseEvent(patient, diagnosisName, vital, testResult,
						vitals.getSuffix(), patientEntity.getLocation(), resultStatus ? "Critical" : "Normal");
			}
			String eventDistributorJson = gson.toJson(eventObject, LinkedHashMap.class);
			// System.out.println(eventDistributorJson);
			EventDistributor eventDistributor = mapper.readValue(eventDistributorJson, EventDistributor.class);
			// System.out.println("checking value::::::"+eventDistributor);
			eventDistributor.setPatientName(patient);
			eventDistributor.setState(patientEntity.getLocation());
			eventDistributor.setLowerLimit(vitals.getLowerLimit());
			eventDistributor.setUpperLimit(vitals.getUpperLimit());
			eventDistributor.setTestStatus(resultStatus ? "Critical" : "Normal");
			eventDistributor.setDiagnosisName(diagnosisName);
			eventDistributor.setDatetime(System.currentTimeMillis());

			eventDistributorServiceImpl.save(eventDistributor);
			String eventBuildMsg = "Patient " + patient + " located at " + patientEntity.getLocation() + " diagnosed "
					+ diagnosisName + " " + vital + " is at  " + testResult + " " + vitals.getSuffix() + "  -  "
					+ eventDistributor.getTestStatus();
			ArrayList<Object> diagoniseList = diagnosisService.findAllDiagnosis();
			ArrayList<Object> vitalsList = thersoldService.findAllVitals();
			ArrayList<Object> patientList = patientService.findAllPatientName();
			eventHash.put("patientList", patientList);
			eventHash.put("diagoniseList", diagoniseList);
			eventHash.put("vitalList", vitalsList);
			eventHash.put("vitalValue", "Vital value ranges from  " + vitals.getLowerLimit() + " " + vitals.getSuffix()
					+ " - " + vitals.getUpperLimit() + " " + vitals.getSuffix());
			eventHash.put("patient", patient);
			eventHash.put("diagnosisName", diagnosisName);
			eventHash.put("vital", vital);
			eventHash.put("testResult", testResult);

			eventHash.put("eventPush", eventBuildMsg);
			System.out.println("HashMapppppppppppp" + eventBuildMsg);
			String eventDistGroovyObject = ResponseDomainUtil.buildEventDis("EventDistributorExtension", eventHash);
			return Response.status(200).entity(eventDistGroovyObject.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Path("/getType/{state}/{typeParam}")
	@Produces("application/json")
	public String getEventsSpecific(@PathParam("state") String stateInfo, @PathParam("typeParam") String typeInfo)
			throws URISyntaxException, IOException {
		log.info("//////////////////////// ::::::::: " + stateInfo + ", typeParam " + typeInfo);
		HashMap hash = new HashMap();
		try {
			ArrayList<Object> patientList = patientService.findAllPatientName();
			ArrayList<Object> diagoniseList = diagnosisService.findAllDiagnosis();
			ArrayList<Object> vitalsList = thersoldService.findAllVitals();

			hash.put("patientList", patientList);
			hash.put("diagoniseList", diagoniseList);
			hash.put("vitalList", vitalsList);
			GroovyObject gryObject = Memory.getGroovyObjects().get("EventDistributorExtension");
			String homeCareJson = (String) gryObject.invokeMethod("buildFIO", hash);
			return homeCareJson;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";
	}

}
