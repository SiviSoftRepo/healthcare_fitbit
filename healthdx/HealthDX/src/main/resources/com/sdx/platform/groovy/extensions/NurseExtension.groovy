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

class NurseExtensions  extends Nurse implements Basics {

	private boolean updateMode = false;

	def buildFIO(HashMap dbref) {

		//Map useridMap  	      	    = FieldUtils.buildTF("idRep","idRep", idRep, false);
		Map nameMap  	       		 = FieldUtils.buildTF("Nurse Name","name", name, false);
		



		def fieldsList = ["components" : [ nameMap, ]];

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