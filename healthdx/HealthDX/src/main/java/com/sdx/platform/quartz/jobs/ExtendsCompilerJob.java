package com.sdx.platform.quartz.jobs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sdx.platform.config.Memory;
import com.sdx.platform.util.ChecksumCalculator;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class ExtendsCompilerJob extends QuartzJobBean {

	@SuppressWarnings("resource")
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		/*
		 * log.info("\n\n---> ExtendsCompilerJob starts");
		 * 
		 * MongoDatabase database = Memory.getMongoClient().getDatabase("test");
		 * 
		 * MongoCollection<Document> coll = database.getCollection("users");
		 * FindIterable<Document> docs = coll.find();
		 * 
		 * for (Document doc : docs) { String ruleName = doc.getString("ruleName");
		 * String ruleURL = doc.getString("ruleURL"); String existingFileContent =
		 * findRuleRegisteredContent(ruleName); if (existingFileContent != null) { try {
		 * String fileContent = FileUtils.readFileToString(new File(ruleURL),
		 * Charset.defaultCharset()); String newMD5 =
		 * ChecksumCalculator.getMD5Hash(fileContent); String exstMD5 =
		 * ChecksumCalculator.getMD5Hash(existingFileContent);
		 * log.info("New MD5 value 	" + newMD5); log.info("EXIST MD5 value 	" +
		 * exstMD5);
		 * 
		 * if (newMD5.equals(exstMD5)) { log.info("No content Change "); } else {
		 * 
		 * log.info("*********** Content Change ");
		 * 
		 * final GroovyClassLoader classLoader = new GroovyClassLoader(); Class<?>
		 * groovy = classLoader.parseClass(fileContent); GroovyObject groovyObj =
		 * (GroovyObject) groovy.newInstance();
		 * 
		 * groovyObj.invokeMethod("convertToJSON", null);
		 * 
		 * Memory.getExtensionsContent().put(ruleName, fileContent);
		 * Memory.getExtensionObjects().put(ruleName, groovyObj); }
		 * 
		 * } catch (IOException | InstantiationException | IllegalAccessException e) {
		 * e.printStackTrace(); }
		 * 
		 * } else { try { String fileContent = FileUtils.readFileToString(new
		 * File(ruleURL), Charset.defaultCharset()); String newMD5 =
		 * ChecksumCalculator.getMD5Hash(fileContent);
		 * 
		 * final GroovyClassLoader classLoader = new GroovyClassLoader(); Class<?>
		 * groovy = classLoader.parseClass(fileContent); GroovyObject groovyObj =
		 * (GroovyObject) groovy.newInstance(); groovyObj.invokeMethod("convertToJSON",
		 * null);
		 * 
		 * Memory.getExtensionsContent().put(ruleName, fileContent);
		 * Memory.getExtensionObjects().put(ruleName, groovyObj);
		 * 
		 * log.info("New MD5 value " + newMD5 + ", Saved to Memory");
		 * 
		 * } catch (IOException | InstantiationException | IllegalAccessException e) {
		 * e.printStackTrace(); } } }
		 * 
		 * log.info("ExtendsCompilerJob Finished");
		 */}

	private boolean findRuleRegistered(String pRuleName) {
		return Memory.getExtensionsContent().containsKey(pRuleName);
	}

	private String findRuleRegisteredContent(String pRuleName) {
		if (Memory.getExtensionsContent().containsKey(pRuleName)) {
			return Memory.getExtensionsContent().get(pRuleName);
		}
		return null;
	}

}
