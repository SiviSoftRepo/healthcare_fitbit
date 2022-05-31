package com.sdx.platform.groovy.extensions


import com.sdx.entity.*;
import com.sdx.platform.groovy.Basics
import groovy.transform.ToString

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.core.mapping.Document
import com.sdx.platform.groovy.FieldUtils

import org.springframework.context.annotation.ComponentScan


import com.sdx.entity.BaseEntity
import com.sdx.entity.*

import java.util.*;
import groovy.json.JsonOutput

class EventDistributorExtension extends EventDistributor implements Basics {

	private boolean updateMode = false;


	def buildFIO(HashMap dbref) {

		ArrayList<Object> patient = dbref.get("patientList");
		ArrayList<Object> vitals = dbref.get("vitalList");
		ArrayList<Object> diagnosis = dbref.get("diagoniseList");
		String eventPush = dbref.get("eventPush");
		String patientVal = dbref.get("patient");
		String diagnosisNameVal = dbref.get("diagnosisName");
		String vitalVal = dbref.get("vital");
		String vitalDtls = dbref.get("vitalValue");	
		String testResult = dbref.get("testResult");
		println(eventPush+"I am in groovy");
		
		Map patientNameMap  	     = FieldUtils.buildETFF("Patient Name","patientName", patient, patientVal , false);
		Map diagnosisNameMap         = FieldUtils.buildETFF("DiagnosisName","diagnosisName", diagnosis,diagnosisNameVal, false);
		Map vitalNameMap   	         = FieldUtils.buildETFF("Vital Name","vitalName", vitals, vitalVal, false);
		Map testResultMap  	       		 = FieldUtils.buildTF("Vital Result Value","testResult", testResult, false);
	    Map vitalDtlsMap  	       		 = FieldUtils.buildTFF("","vitalVal", vitalDtls, false);
		Map pushResultMap  	       		 = FieldUtils.buildTFF("","result", eventPush, false);


		def fieldsList = ["components" : [patientNameMap, diagnosisNameMap, vitalNameMap, vitalDtlsMap,testResultMap,pushResultMap]];

		return JsonOutput.toJson(fieldsList);
	}

	@Override
	public String toString() {
		return diagnosisName;
	}

	def setUpdateMode(boolean updatable) {
		println("Update Mode set to "+updatable)
		updateMode = updatable;
	}
}