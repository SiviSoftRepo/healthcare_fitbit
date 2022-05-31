package com.sdx.platform.groovy

import java.util.stream.Collectors

import com.sdx.platform.config.Memory

import groovy.json.JsonSlurper

class FieldUtils {

	static Map buildTF(String pLabel, String pKey, Object defValue, boolean isNum) {
		def tfMap = [
			type 			: "textfield",
			label 			: pLabel,
			key 			: pKey,
			defaultValue	: defValue != null ? defValue : "",
			inputType		: isNum ? "number" : "text",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pLabel,
			display         : "form"

		];
		return tfMap;
	}

	
	static Map buildTFF(String pLabel, String pKey, Object defValue, boolean isNum) {
		def tfMap = [
			type 			: "textfield",
			label 			: pLabel,
			key 			: pKey,
			defaultValue	: defValue,
			inputType		: isNum ? "number" : "text",
			disabled 		: "true",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pLabel,
			display         : "form"

		];
		return tfMap;
	}
	
	static Map buildTA(String pLabel, String pKey, Object defValue, boolean isNum) {
		def tfMap = [
			type 			: "textarea",
			label 			: pLabel,
			key 			: pKey,
			defaultValue	: defValue != null ? defValue : "",
			inputType		: isNum ? "number" : "text",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pLabel,
			display         : "form"

		];
		return tfMap;
	}
	
	
	

	
	static Map buildNF(String pLabel, String pKey, Object defValue, boolean isNum) {
		def tfMap = [
			type 			: "datetime",
			label 			: pLabel,
			key 			: pKey,
			"format"        : "yyyy-MM-dd ",
			defaultValue	: defValue != null ? defValue : "",
			inputType		: isNum ? "number" : "text",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pLabel,
			display         : "form"

		];
		return tfMap;
	}


	static Map buildETF(String pLabel, String pKey, Object defValue, boolean isNum) {
		def tfMap = [
			type 			: "select",
			label 			: pLabel,
			key 			: pKey,
			defaultValue	: defValue != null ? defValue : "",
			inputType		: isNum ? "number" : "text",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pLabel,
			display         : "form"

		];
		return tfMap;
	}

	/*static Map buildETFF(String label, String pKey, Object Value, String lvalue) {
		def tfMap = [
			"type": "form",
			type 			: "select",
			label 			: label,
			key 			: pKey,
			dataSrc			: "values",
			data			: ["values":Value != null ? Value : ""],
			defaultValue	: lvalue,
			inputType		: "select",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pKey,
			display         : "form"


		];
		return tfMap;
	}*/
	
	static Map buildETFF(String label, String pKey, Object Value, String lvalue , boolean isNum) {
		def tfMap = [
			"type": "form",
			type 			: "select",
			label 			: label,
			key 			: pKey,
			dataSrc			: "values",
			data			: ["values":Value != null ? Value : ""],
			defaultValue	: lvalue,
			inputType		: isNum ? "number" : "text",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pKey,
			display         : "form"


		];
		return tfMap;
	}
	
	
	
	static Map buildSELECT(String label, String pKey, String valuesString, String defValue , boolean isMulti) {
		
		def valList = new JsonSlurper().parseText(valuesString);
		print("valList "+valList)
		
		def tfMap = [
			"type": "form",
			type 			: "select",
			label 			: label,
			key 			: pKey,
			dataSrc			: "values",
			data			: [ "values": valList ],
			defaultValue	: defValue,
			multiple		: isMulti ? true : false,
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pKey,
			template: 		"<span>{{ item.label }}</span>",
			display         : "form"

		];
		System.out.println("tfMap "+tfMap);
		return tfMap;
	}


	static Map buildButton(String pLabel, String pKey, String pAction) {
		def tfMap = [
			type 			: 	"button",
			label 			: 	pLabel,
			action 			: 	pAction,
			theme			: 	"primary ",
			leftIcon		: 	"fa-plus"

		];
		return tfMap;
	}



	static Map buildPhoneField(String pLabel, String pKey, Object defValue) {
		def tfMap = [
			type 			: "number",
			label 			: pLabel,
			key 			: pKey,
			input 			: true,
			inputMask		: "(999) 999-9999",
			defaultValue	: defValue != null ? defValue : "",
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Enter "+pLabel

		];
		return tfMap;
	}

	static Map buildDTF(String pLabel, String pKey, Object defValue, boolean isNum) {
		def dtfMap = [
			type 			: "datetime",
			label 			: pLabel,
			key 			: pKey,
			defaultValue	: defValue != null ? defValue : "",
			inputType		: isNum ? "number" : "text",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Pick a Date",
			display         : "form"

		];
		return dtfMap;
	}
	
	static Map buildCal(String pLabel,int fValue, int sValue, boolean isNum) {
		def dtfMap = [
			type 			: "textfield",
			label 			: pLabel,
			key 			: pLabel,
			//defaultValue	: defValue != null ? defValue : "",
			inputType		: isNum ? "number" : "text",
			input 			: true,
			clearOnHide		: true,
			"hidden"		: false,
			placeholder		: "Pick a Date",
			display         : "form",
			calculateValue:[
				"-":[
				   "var": fValue,
				   "var": sValue
				]]
			  
		
		];
		
		
		return dtfMap;
	}
	
	
	
	
	
}
