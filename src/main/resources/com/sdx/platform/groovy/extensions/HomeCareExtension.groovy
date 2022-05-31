package com.sdx.platform.groovy.extensions

import com.sdx.entity.*
import com.sdx.platform.groovy.Basics
import groovy.transform.ToString

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.core.mapping.Document
import com.sdx.platform.groovy.FieldUtils

import org.springframework.context.annotation.ComponentScan
import com.sdx.platform.config.Memory;

import com.sdx.entity.BaseEntity
import com.sdx.entity.*

import java.util.*;
import groovy.json.JsonOutput




class HomeCareExtensions  extends HomeCare implements Basics {
	
	
	private boolean updateMode = false;
	
	
	def buildFIO(HashMap dbref) {
		
		String defaultLocation = dbref.get("defaultLocationName");
		//Map useridMap  	      	= FieldUtils.buildTF("idRep","idRep", idRep, false);
		Map nameMap  	       	 	= FieldUtils.buildTF("Name","name", name, false);
		Map locationMap           	= FieldUtils.buildSELECT("Location", "location", Memory.getUSA_MAP_VAL_LABELS(), defaultLocation, false);
		Map roomTempMap   	        = FieldUtils.buildTF("Room Temperature","roomTemp", roomTemp, false);
		Map roomHumidityMap  	    = FieldUtils.buildTF("Room Humidity","roomHumidity", roomHumidity, false);
		Map lightOnOffMap      		= FieldUtils.buildTF("Lights On/Off","lightOnOff", lightOnOff, false);
		Map frontDoorMap            = FieldUtils.buildTF("Front Door","frontDoor", frontDoor, false);
		
		//Map buildSELECT(String label, String pKey, HashMap<String, String> valueMap, String defValue , boolean isMulti) {
		
		
		def fieldsList = ["components" : [nameMap, locationMap, roomTempMap,roomHumidityMap,lightOnOffMap,frontDoorMap]];
		
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

