package com.sdx.platform.groovy.extensions

import com.sdx.platform.config.Memory;
import com.sdx.entity.*
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

class PatientExtensions  extends Patient implements Basics {

	private boolean updateMode = false;

	def buildFIO(HashMap dbref) {

		println("inside PatientExtensions :::::::::::::::: " +dbref );


		ArrayList<Object> doctor 	= dbref.get("Doctor");
		ArrayList<Object> nurse 	= dbref.get("Nurse");
		ArrayList<Object> diagnosis = dbref.get("Diagnosis");
		ArrayList<Object> homecare 	= dbref.get("Homecare");
		
		
		String defaultDoctorName 	=  dbref.get("defaultDoctorName");
		String defaultDiagnosisName =  dbref.get("defaultDiagnosisName");
		String defaultNurseNameName = dbref.get("defaultNurseNameName");
		String defaultHomeCare 		= dbref.get("defaultHomeCare");
		
		String defaultGender = dbref.get("defaultGender");
		String defaultStatus = dbref.get("defaultStatus");
		String defaultLocation = dbref.get("defaultLocation");

		ArrayList<Object> gender = new ArrayList<Object>();
		gender.add("Male");
		gender.add("Female");

		ArrayList<Object> status = new ArrayList<Object>();
		status.add("Critical");
		status.add("Immediate Attention Required");
		status.add("Stable");
		status.add("Normal");
		
		
		//ArrayList<Object> userRolesId = dbref.get("UserRole");
		//Map useridMap  	      	    = FieldUtils.buildTF("idRep","idRep", idRep, false);
		Map nameMap  	       		 	= FieldUtils.buildTF("Name","name", name, false);
		Map genderMap           		= FieldUtils.buildETFF("Gender","gender", gender, defaultGender, false);
		Map DOBMap   	        		= FieldUtils.buildNF("Dob","dob", dob, false);
		Map heightMap  	        		= FieldUtils.buildTF("Height","height", height, false);
		Map weightMap      				= FieldUtils.buildTF("Weight","weight", weight, false);
		Map DiagnosisrMap             	= FieldUtils.buildETFF("Diagnosis","diagnosis",diagnosis, defaultDiagnosisName, false);
		Map homecareMap            		= FieldUtils.buildETFF("Homecare","homecare",homecare, defaultHomeCare, false);
		
		Map doctorMap             		= FieldUtils.buildETFF("Doctor","doctor",doctor, defaultDoctorName, false);
		Map nurseMap             		= FieldUtils.buildETFF("Nurse","nurse",nurse, defaultNurseNameName, false);
		Map statusMap           		= FieldUtils.buildETFF("Status","status", status,defaultStatus, false);
		Map locationMap           		= FieldUtils.buildSELECT("From Location", "location", Memory.getUSA_MAP_VAL_LABELS(), defaultLocation, false);
		
		
		
		def fieldsList = ["components" : [nameMap,genderMap, locationMap ,homecareMap, DOBMap,heightMap,weightMap,DiagnosisrMap,doctorMap,nurseMap,statusMap]];
		
		return JsonOutput.toJson(fieldsList);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	def setUpdateMode(boolean updatable) {
		println("Update Mode set to "+updatable)
		updateMode = updatable;
	}
	
}

