package com.sdx.platform.domain;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.sdx.platform.config.Memory;
import com.sdx.entity.*;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseDomainUtil {

	public static JSONObject buildHomeCareEditDetails(Document document, String extensionName) throws JSONException {
		System.out.println("DOC HEALTH>>" + document + "EXT" + extensionName);
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		if (Memory.getExtensionObjects().containsKey(extensionName)) {
			System.out.println("document is " + document.toString());
			GroovyObject rfJson = (GroovyObject) JSON.parseObject(document.toJson(),
					Memory.getExtensionObjects().get(extensionName).getClass());
			log.info("RF-JSON" + rfJson);

			rfJson.invokeMethod("setUpdateMode", true);
			// rfJson.invokeMethod("convertToJSON", null);
			String json = (String) rfJson.invokeMethod("buildFIO", null);

			log.info("JSON >>>>>>>>>> " + json);
			return new JSONObject(json);
		}
		return null;
	}

	public static JSONObject buildExtensionDetails(Document document, String extensionName) throws JSONException {
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		if (Memory.getExtensionObjects().containsKey(extensionName)) {
			System.out.println("document is " + document.toString());
			GroovyObject rfJson = (GroovyObject) JSON.parseObject(document.toJson(),
					Memory.getExtensionObjects().get(extensionName).getClass());
			log.info("RF-JSON" + rfJson);

			rfJson.invokeMethod("setUpdateMode", true);
			// rfJson.invokeMethod("convertToJSON", null);
			String json = (String) rfJson.invokeMethod("buildFIO", null);

			log.info("JSON >>>>>>>>>> " + json);
			return new JSONObject(json);
		}
		return null;
	}

	public static String buildRefExtension(String extensionName, HashMap refDbrefMap) throws JSONException {
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		try {
			if (Memory.getExtensionObjects().containsKey(extensionName)) {
				GroovyObject gryObject = Memory.getGroovyObjects().get(extensionName);
				String json = (String) gryObject.invokeMethod("buildFIO", refDbrefMap);
				return json;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}

	public static JSONObject buildEditNew(Document document, String extensionName, HashMap ref) throws JSONException {
		System.out.println("DOC HEALTH>>" + document + "EXT" + extensionName);
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		if (Memory.getExtensionObjects().containsKey(extensionName)) {
			System.out.println("document is " + document.toString());
			GroovyObject rfJson = (GroovyObject) JSON.parseObject(document.toJson(),
					Memory.getExtensionObjects().get(extensionName).getClass());
			log.info("RF-JSON" + rfJson);

			rfJson.invokeMethod("setUpdateMode", true);
			// rfJson.invokeMethod("convertToJSON", null);
			String json = (String) rfJson.invokeMethod("buildFIO", ref);

			log.info("JSON >>>>>>>>>> " + json);
			return new JSONObject(json);
		}
		return null;
	}

	public static String buildEventDis(String extensionName, HashMap refDbrefMap) throws JSONException {
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		try {
			if (Memory.getExtensionObjects().containsKey(extensionName)) {
				GroovyObject gryObject = Memory.getGroovyObjects().get(extensionName);
				String json = (String) gryObject.invokeMethod("buildFIO", refDbrefMap);
				return json;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{Error Occured}";

	}

	public static JSONObject buildMedicineEditDetails(Document document, String extensionName, HashMap medicinedbref) {
		System.out.println("DOC HEALTH>>" + document + "EXT" + extensionName);
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		if (Memory.getExtensionObjects().containsKey(extensionName)) {
			System.out.println("document is " + document.toString());
			GroovyObject rfJson = (GroovyObject) JSON.parseObject(document.toJson(),
					Memory.getExtensionObjects().get(extensionName).getClass());
			log.info("RF-JSON" + rfJson);

			rfJson.invokeMethod("setUpdateMode", true);
			// rfJson.invokeMethod("convertToJSON", null);
			String json = (String) rfJson.invokeMethod("buildFIO", medicinedbref);

			log.info("JSON >>>>>>>>>> " + json);
			return new JSONObject(json);
		}
		return null;
	}

	public static JSONObject buildVitalsEditDetails(Document document, String extensionName, HashMap vitaldbref) {
		System.out.println("DOC HEALTH>>" + document + "EXT" + extensionName);
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		if (Memory.getExtensionObjects().containsKey(extensionName)) {
			System.out.println("document is " + document.toString());
			GroovyObject rfJson = (GroovyObject) JSON.parseObject(document.toJson(),
					Memory.getExtensionObjects().get(extensionName).getClass());
			log.info("RF-JSON" + rfJson);

			rfJson.invokeMethod("setUpdateMode", true);
			// rfJson.invokeMethod("convertToJSON", null);
			String json = (String) rfJson.invokeMethod("buildFIO", vitaldbref);

			log.info("JSON >>>>>>>>>> " + json);
			return new JSONObject(json);
		}
		return null;
	}

	public static JSONObject buildDiagnosisEditDetails(Document document, String extensionName, HashMap diagnosisMap) {
		System.out.println("DOC HEALTH>>" + document + "EXT" + extensionName);
		log.info("Available :::::::::: " + Memory.getExtensionObjects().containsKey(extensionName));
		if (Memory.getExtensionObjects().containsKey(extensionName)) {
			System.out.println("document is " + document.toString());
			GroovyObject rfJson = (GroovyObject) JSON.parseObject(document.toJson(),
					Memory.getExtensionObjects().get(extensionName).getClass());
			log.info("RF-JSON" + rfJson);

			rfJson.invokeMethod("setUpdateMode", true);
			// rfJson.invokeMethod("convertToJSON", null);
			String json = (String) rfJson.invokeMethod("buildFIO", diagnosisMap);

			log.info("JSON >>>>>>>>>> " + json);
			return new JSONObject(json);
		}
		return null;
	}

}
