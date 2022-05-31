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

class DiagnosisExtensions extends Diagnosis implements Basics {

	private boolean updateMode = false;


	def buildFIO(HashMap dbref) {
		
		
		
		ArrayList<Object> thresold = dbref.get("thresold");
		String defaultThresold = dbref.get("defaultName");

		//Map useridMap  	      	   = FieldUtils.buildTF("idRep","idRep", idRep, false);
		Map diseasesNameMap  	       = FieldUtils.buildTF("Disease Name","diseasesName", diseasesName, false);
		Map specialistNameMap          = FieldUtils.buildTF("Specialist Name","specialistName", specialistName, false);
		Map hospitalNameMap   	       = FieldUtils.buildTF("Hospital Name","hospitalName", hospitalName, false);
		Map doctorInOutMap  	       = FieldUtils.buildTF("Doctor In/Out","doctorIn", doctorIn, false);
		Map thresoldMap  	        	= FieldUtils.buildETFF("Thersold","thresold", thresold, defaultThresold, false);



		def fieldsList = ["components" : [
				diseasesNameMap,
				specialistNameMap,
				hospitalNameMap,
				doctorInOutMap,
				thresoldMap
			]];

		return JsonOutput.toJson(fieldsList);
	}

	@Override
	public String toString() {
		return diseasesName;
	}

	def setUpdateMode(boolean updatable) {
		println("Update Mode set to "+updatable)
		updateMode = updatable;
	}
}