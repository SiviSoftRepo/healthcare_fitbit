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

class MedicineExtension  extends MedicineDevice implements Basics {

	private boolean updateMode = false;

	def buildFIO(HashMap dbref) {
		
		String defaultTypes = dbref.get("defaultTypes");
		print("DefaultType::::" + defaultTypes);
		
		ArrayList<Object> type = new ArrayList<Object>();
		type.add("Wearable");
		type.add("Non Wearable");
		
		Map nameMap  	       		    = FieldUtils.buildTF("Name","name", name, false);
		Map deviceIdMap           	    = FieldUtils.buildTF("deviceId","deviceId", deviceId, false);
		Map typeMap   	                = FieldUtils.buildETFF("Type","type", type, defaultTypes, false);
		Map manufacturerMap  	        = FieldUtils.buildTF("manufacturer","manufacturer", manufacturer, false);
		Map serialNoMap                 = FieldUtils.buildTF("serialNo","serialNo", serialNo, false);
		Map caliberatedMap              = FieldUtils.buildNF("Caliberated Date","caliberated", caliberated, false);
		Map caliberationMap             = FieldUtils.buildNF("Caliberation Date","caliberation", caliberation, false);

		def fieldsList = ["components" : [
				nameMap,
				deviceIdMap,
				typeMap,
				manufacturerMap,
				serialNoMap,
				caliberatedMap,
				caliberationMap
			]];

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