package com.sdx.platform.groovy.extensions

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




class HomeCareExtensions  extends Doctor implements Basics {
	
	
	
	
	private boolean updateMode = false;
	
	
	def buildFIO(HashMap dbref) {
		
		
		String defaultShift = dbref.get("defaultShift");
		String defaultAvail = dbref.get("defaultAvail");
		
		ArrayList<Object> shift = new ArrayList<Object>();
		shift.add("Morning");
		shift.add("Afternoon");
		shift.add("Evening");
		shift.add("Night");
		
		ArrayList<Object> avalability = new ArrayList<Object>();
		avalability.add("Avalaible");
		avalability.add("Not Avalaible");
		
		
		
		//Map useridMap  	      	    = FieldUtils.buildTF("idRep","idRep", idRep, false);
		Map nameMap  	       		 = FieldUtils.buildTF("Name","doctorName", doctorName, false);
		Map specialistMap           	= FieldUtils.buildTF("Specialist","specialist", specialist, false);
		Map shiftMap           	= FieldUtils.buildETFF("Shift","shift", shift, defaultShift,true);
		Map availableMap           	= FieldUtils.buildETFF("Availability","availability", avalability,defaultAvail,true);
		
		/*def datagrid1 = [
			label  : "",
			type    : "datagrid",
			disableAddingRemovingRows : true,
			components :[nameMap, specialistMap]];
		
		
		def datagrid2 = [
			label  : "",
			type    : "datagrid",
			disableAddingRemovingRows : true,
			components :[shiftMap, availableMap ]];
		*/
		
		def fieldsList = ["components" : [nameMap , specialistMap,shiftMap,availableMap]];
		
		return JsonOutput.toJson(fieldsList);
	}
	
	@Override
	public String toString() {
		return doctorName;
	}
	
	def setUpdateMode(boolean updatable) {
		println("Update Mode set to "+updatable)
		updateMode = updatable;
	}
	
}

