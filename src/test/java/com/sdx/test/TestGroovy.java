package com.sdx.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

public class TestGroovy {
	
	public static void main(String[] args) {
		TestGroovy.doTest();
	}

	private static void doTest() {
		final GroovyClassLoader classLoader = new GroovyClassLoader();
		String gruleCtnt;
		try {
			gruleCtnt = FileUtils.readFileToString(
					new File("C:\\fMounts\\Code\\downloads\\CHROME\\quartz-demo-master\\quartz-demo-master\\src\\main\\resources\\com\\sdx\\platform\\groovy\\UserExtension.groovy"),
					Charset.defaultCharset());
			

	        Class<?> groovy = classLoader.parseClass(gruleCtnt.toString());
	        //GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
	        GroovyObject groovyObj = (GroovyObject) JSON.parseObject(buildUserJSONInput(), groovy);
	        
	        groovyObj.invokeMethod("buildFIO", null);
	        
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static String buildUserJSONInput() throws IOException {
		return FileUtils.readFileToString(new File("P:\\UI-Stuffs\\SDX-Application\\prototype\\SDX-App\\data\\usr.json"),
				Charset.defaultCharset());
	}

}
