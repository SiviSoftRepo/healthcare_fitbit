package com.sdx.platform.groovy.extensions

import com.sdx.entity.*
import com.sdx.platform.groovy.Basics
import groovy.transform.ToString

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.core.mapping.Document
import com.sdx.platform.groovy.FieldUtils

import org.springframework.context.annotation.ComponentScan



import java.util.*;
import groovy.json.JsonOutput




class TeleMedicineExtension  extends Telemedicine implements Basics {
	
	
	
	
	private boolean updateMode = false;
	
	
	def buildFIO(HashMap dbref) {
		
		ArrayList<Object> departmentId = dbref.get("Department");
		print("inside groovy" +departmentId);
		
		ArrayList<Object> userRolesId = dbref.get("UserRole");
		//Map useridMap  	      	    = FieldUtils.buildTF("idRep","idRep", idRep, false);
		Map nameMap  	       		 = FieldUtils.buildTF("Name","name", name, false);
		Map deviceIdMap           	= FieldUtils.buildTF("deviceId","deviceId", deviceId, false);
		Map typeMap   	        = FieldUtils.buildTF("type","type", type, false);
		Map manufacturerMap  	        = FieldUtils.buildTF("manufacturer","manufacturer", manufacturer, false);
		Map serialNoMap      = FieldUtils.buildTF("serialNo","serialNo", serialNo, false);
		//Map sPAddressMap             = FieldUtils.buildTF("sPAddress","sPAddress", address, false);
		Map caliberatedDateMap      = FieldUtils.buildTF("caliberatedDate","caliberatedDate", caliberatedDate, false);
		Map caliberationDateMap             = FieldUtils.buildTF("caliberationDate","caliberationDate", caliberationDate, false);
		
		
		
		def fieldsList = ["components" : [nameMap,deviceIdMap,typeMap,manufacturerMap,serialNoMap,caliberatedDateMap,]];
		
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

