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



class VitalsExtension  extends Vitals implements Basics {

	private boolean updateMode = false;

	def buildFIO(HashMap dbref) {

	String defaultSuffix = dbref.get("defaultSuffix");
		print("DefaultSuffix::::" + defaultSuffix);
		
		ArrayList<Object> suffix = new ArrayList<Object>();
		suffix.add("mmHg");
		suffix.add("Fahrenheit");
		
		Map diseaseNameMap  	       		= FieldUtils.buildTF("Disease Name","name", name, false);
		Map lowerLimitMap  	                = FieldUtils.buildTF("Lower Limit","lowerLimit", lowerLimit, false);
		Map upperLimitMap      			    = FieldUtils.buildTF("Upper Limit","upperLimit", upperLimit, false);
		Map suffixMap                       = FieldUtils.buildETFF("Suffix","suffix", suffix, defaultSuffix, false);

		def fieldsList = ["components" : [
				diseaseNameMap,
				lowerLimitMap,
				upperLimitMap,
				suffixMap
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