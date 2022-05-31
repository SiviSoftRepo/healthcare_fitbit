package com.sdx.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
	
	@POST
	@Path("/mat")
	public String mat(String payload) {

		String OnBed = "No"; // Default No Patient
		String PatientName = "Kallis";
		String result = "";
		int min = 1024;

		payload = payload.replace("\"", "");
		String[] words = payload.split(",");
		int k = 0, R = 0, C = 1, position;
		for (int i = 0; i < 48; i++) {
			if (k % 6 == 0) {
				System.out.print("\n");
				R = R + 1; // Row
				C = 1; // Column
			}

			position = Integer.parseInt(words[i]);

			if (R == 1) {
				if (position >= min) {
					result = result + "Top" + ",";
				}
			}
			if (R == 8) {
				if (position >= min) {
					result = result + "Bottom" + ",";
				}
			}

			if (C == 1) {
				if (position > min) {
					result = result + "Left" + ",";
				}
			}
			if (C == 6) {
				if (position > min) {
					result = result + "Right" + ",";
				}
			}

			if (position > min) {
				OnBed = "Yes";
			}

			System.out.print(words[i] + "(R" + R + "," + "C" + C + ")\t");
			k = k + 1;
			C = C + 1;
		}
		if (result == "") {
			result = "No Patient" + ",";
		}

		String[] strWords = result.split(",");

		LinkedHashSet<String> lhSetWords = new LinkedHashSet<String>(Arrays.asList(strWords));

		StringBuilder sbTemp = new StringBuilder();
		int index = 0;

		for (String s : lhSetWords) {

			if (index > 0)
				sbTemp.append("");

			sbTemp.append(s);
			index++;
		}

		result = sbTemp.toString();

		System.out.println("Result : " + result);

		try {
			HealthCareEventGen.buildmatHttps(PatientName, OnBed, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payload;
	}


}
