package com.sdx.platform.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import groovy.lang.GroovyObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Memory {

	private static MongoClient mongoClient = null;
	private static MongoDatabase sdxBaseDB = null;

	static {
		/*
		 * log.info("*************** KICK Start MEMORY ******************");
		 * ConnectionString connString = new
		 * ConnectionString("mongodb://localhost:27017"); MongoClientSettings settings =
		 * MongoClientSettings.builder().applyConnectionString(connString).retryWrites(
		 * true) .build();
		 * 
		 * mongoClient = MongoClients.create(settings); sdxBaseDB =
		 * Memory.getMongoClient().getDatabase("test"); log.info("Built MongoClient " +
		 * mongoClient);
		 */
	}
	private static ConcurrentHashMap<String, Object> appProperties = new ConcurrentHashMap<>();

	private static LinkedHashMap<String, String> extensionsContent = new LinkedHashMap<>();

	private static LinkedHashMap<String, GroovyObject> extensionObjects = new LinkedHashMap<>();
	private static ConcurrentHashMap<String, GroovyObject> groovyObjects = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, String> groovyContent = new ConcurrentHashMap<>();
	
	private static String USA_MAP_VAL_LABELS						= null;
	private static HashMap<String, String> USA_MAPS					= null;

	public void add2AppProperties(String pKey, Object pValue) {
		appProperties.put(pKey, pValue);
	}

	public static ConcurrentHashMap<String, Object> getAppProperties() {
		if (appProperties == null) {
			appProperties = new ConcurrentHashMap<String, Object>();
		}
		return appProperties;
	}

	public static void setAppProperties(ConcurrentHashMap<String, Object> appProperties) {
		Memory.appProperties = appProperties;
	}

	public static LinkedHashMap<String, String> getExtensionsContent() {
		return extensionsContent;
	}

	public static void setExtensionsContent(LinkedHashMap<String, String> extensionsContent) {
		Memory.extensionsContent = extensionsContent;
	}

	public static LinkedHashMap<String, GroovyObject> getExtensionObjects() {
		return extensionObjects;
	}

	public static void setExtensionObjects(LinkedHashMap<String, GroovyObject> extensionObjects) {
		Memory.extensionObjects = extensionObjects;
	}

	public static void init() {
		log.info("Initializing the SDX Memory");
	}

	public static MongoClient getMongoClient() {
		return mongoClient;
	}

	public static void setMongoClient(MongoClient mongoClient) {
		Memory.mongoClient = mongoClient;
	}

	public static MongoDatabase getSdxBaseDB() {
		return sdxBaseDB;
	}

	public static void setSdxBaseDB(MongoDatabase sdxBaseDB) {
		Memory.sdxBaseDB = sdxBaseDB;
	}

	public static ConcurrentHashMap<String, GroovyObject> getGroovyObjects() {
		return groovyObjects;
	}

	public static void setGroovyObjects(ConcurrentHashMap<String, GroovyObject> groovyObjects) {
		Memory.groovyObjects = groovyObjects;
	}

	public static ConcurrentHashMap<String, String> getGroovyContent() {
		return groovyContent;
	}

	public static void setGroovyContent(ConcurrentHashMap<String, String> groovyContent) {
		Memory.groovyContent = groovyContent;
	}

	public static HashMap<String, String> getUSA_MAPS() {
		if (USA_MAPS == null) {
			USA_MAPS = new LinkedHashMap<String, String>();
			USA_MAPS.put("US-VA",	"Virginia");
			USA_MAPS.put("US-PA",	"Pennsylvania");
			USA_MAPS.put("US-TN",	"Tennessee");
			USA_MAPS.put("US-WV",	"West Virginia");
			USA_MAPS.put("US-NV",	"Nevada");
			USA_MAPS.put("US-TX",	"Texas");
			USA_MAPS.put("US-NH",	"New Hampshire");
			USA_MAPS.put("US-NY",	"New York");
			USA_MAPS.put("US-HI",	"Hawaii");
			USA_MAPS.put("US-VT",	"Vermont");
			USA_MAPS.put("US-NM",	"New Mexico");
			USA_MAPS.put("US-NC",	"North Carolina");
			USA_MAPS.put("US-ND",	"North Dakota");
			USA_MAPS.put("US-NE",	"Nebraska");
			USA_MAPS.put("US-LA",	"Louisiana");
			USA_MAPS.put("US-SD",	"South Dakota");
			USA_MAPS.put("US-DC",	"District of Columbia");
			USA_MAPS.put("US-DE",	"Delaware");
			USA_MAPS.put("US-FL",	"Florida");
			USA_MAPS.put("US-CT",	"Connecticut");
			USA_MAPS.put("US-WA",	"Washington");
			USA_MAPS.put("US-KS",	"Kansas");
			USA_MAPS.put("US-WI",	"Wisconsin");
			USA_MAPS.put("US-OR",	"Oregon");
			USA_MAPS.put("US-KY",	"Kentucky");
			USA_MAPS.put("US-ME",	"Maine");
			USA_MAPS.put("US-OH",	"Ohio");
			USA_MAPS.put("US-OK",	"Oklahoma");
			USA_MAPS.put("US-ID",	"Idaho");
			USA_MAPS.put("US-WY",	"Wyoming");
			USA_MAPS.put("US-UT",	"Utah");
			USA_MAPS.put("US-IN",	"Indiana");
			USA_MAPS.put("US-IL",	"Illinois");
			USA_MAPS.put("US-AK",	"Alaska");
			USA_MAPS.put("US-NJ",	"New Jersey");
			USA_MAPS.put("US-CO",	"Colorado");
			USA_MAPS.put("US-MD",	"Maryland");
			USA_MAPS.put("US-MA",	"Massachusetts");
			USA_MAPS.put("US-AL",	"Alabama");
			USA_MAPS.put("US-MO",	"Missouri");
			USA_MAPS.put("US-MN",	"Minnesota");
			USA_MAPS.put("US-CA",	"California");
			USA_MAPS.put("US-IA",	"Iowa");
			USA_MAPS.put("US-MI",	"Michigan");
			USA_MAPS.put("US-GA",	"Georgia");
			USA_MAPS.put("US-AZ",	"Arizona");
			USA_MAPS.put("US-MT",	"Montana");
			USA_MAPS.put("US-MS",	"Mississippi");
			USA_MAPS.put("US-SC",	"South Carolina");
			USA_MAPS.put("US-RI",	"Rhode Island");
			USA_MAPS.put("US-AR",	"Arkansas");
		}
		return USA_MAPS;
	}

	public static void setUSA_MAPS(HashMap<String, String> usaMapDetails) {
		USA_MAPS = usaMapDetails;
	}

	public static String getUSA_MAP_VAL_LABELS() {
		if (StringUtils.isBlank(USA_MAP_VAL_LABELS)) {
			USA_MAP_VAL_LABELS = "[{ 'value' : 'US-VA', 'label' : 'Virginia'}, { 'value' : 'US-PA', 'label' : 'Pennsylvania'}, { 'value' : 'US-TN', 'label' : 'Tennessee'}, "
					+ "{ 'value' : 'US-WV', 'label' : 'West Virginia'}, { 'value' : 'US-NV', 'label' : 'Nevada'}, { 'value' : 'US-TX', 'label' : 'Texas'}, { 'value' : 'US-NH', 'label' : 'New Hampshire'}, "
					+ "{ 'value' : 'US-NY', 'label' : 'New York'}, { 'value' : 'US-HI', 'label' : 'Hawaii'}, { 'value' : 'US-VT', 'label' : 'Vermont'}, { 'value' : 'US-NM', 'label' : 'New Mexico'}, "
					+ "{ 'value' : 'US-NC', 'label' : 'North Carolina'}, { 'value' : 'US-ND', 'label' : 'North Dakota'}, { 'value' : 'US-NE', 'label' : 'Nebraska'}, { 'value' : 'US-LA', 'label' : 'Louisiana'}, "
					+ "{ 'value' : 'US-SD', 'label' : 'South Dakota'}, { 'value' : 'US-DC', 'label' : 'District of Columbia'}, { 'value' : 'US-DE', 'label' : 'Delaware'}, { 'value' : 'US-FL', 'label' : 'Florida'}, "
					+ "{ 'value' : 'US-CT', 'label' : 'Connecticut'}, { 'value' : 'US-WA', 'label' : 'Washington'}, { 'value' : 'US-KS', 'label' : 'Kansas'}, { 'value' : 'US-WI', 'label' : 'Wisconsin'}, "
					+ "{ 'value' : 'US-OR', 'label' : 'Oregon'}, { 'value' : 'US-KY', 'label' : 'Kentucky'}, { 'value' : 'US-ME', 'label' : 'Maine'}, { 'value' : 'US-OH', 'label' : 'Ohio'}, "
					+ "{ 'value' : 'US-OK', 'label' : 'Oklahoma'}, { 'value' : 'US-ID', 'label' : 'Idaho'}, { 'value' : 'US-WY', 'label' : 'Wyoming'}, { 'value' : 'US-UT', 'label' : 'Utah'}, "
					+ "{ 'value' : 'US-IN', 'label' : 'Indiana'}, { 'value' : 'US-IL', 'label' : 'Illinois'}, { 'value' : 'US-AK', 'label' : 'Alaska'}, { 'value' : 'US-NJ', 'label' : 'New Jersey'}, "
					+ "{ 'value' : 'US-CO', 'label' : 'Colorado'}, { 'value' : 'US-MD', 'label' : 'Maryland'}, { 'value' : 'US-MA', 'label' : 'Massachusetts'}, { 'value' : 'US-AL', 'label' : 'Alabama'}, "
					+ "{ 'value' : 'US-MO', 'label' : 'Missouri'}, { 'value' : 'US-MN', 'label' : 'Minnesota'}, { 'value' : 'US-CA', 'label' : 'California'}, { 'value' : 'US-IA', 'label' : 'Iowa'}, "
					+ "{ 'value' : 'US-MI', 'label' : 'Michigan'}, { 'value' : 'US-GA', 'label' : 'Georgia'}, { 'value' : 'US-AZ', 'label' : 'Arizona'}, { 'value' : 'US-MT', 'label' : 'Montana'}, "
					+ "{ 'value' : 'US-MS', 'label' : 'Mississippi'}, { 'value' : 'US-SC', 'label' : 'South Carolina'}, { 'value' : 'US-RI', 'label' : 'Rhode Island'}, { 'value' : 'US-AR', 'label' : 'Arkansas'}]";
			USA_MAP_VAL_LABELS = StringUtils.replaceAll(USA_MAP_VAL_LABELS, "'", "\"");
		}
		System.out.println(USA_MAP_VAL_LABELS);
		return USA_MAP_VAL_LABELS;
	}

	public static void setUSA_MAP_VAL_LABELS(String uSA_MAP_VAL_LABELS) {
		USA_MAP_VAL_LABELS = uSA_MAP_VAL_LABELS;
	}

}
