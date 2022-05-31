package com.sdx.platform.quartz;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.control.CompilationFailedException;

import com.sdx.platform.config.Memory;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultActions {

	public static void init() {
		URL res = DefaultActions.class.getClassLoader().getResource("com/sdx/platform/groovy/extensions");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("com/sdx/platform/groovy/extensions");
		String path = url.getPath();
		System.out.println("ALL files " + Arrays.asList(new File(path).listFiles()));

		final GroovyClassLoader classLoader = new GroovyClassLoader();

		List<File> groovies = Arrays.asList(new File(path).listFiles());
		groovies.stream().forEach(new Consumer<File>() {
			public void accept(File gFile) {
				Class<?> groovy;
				try {
					System.out.println("Current process " + gFile.getName());
					groovy = classLoader.parseClass(FileUtils.readFileToString(gFile, Charset.defaultCharset()));
					GroovyObject groovyObj;

					try {
						groovyObj = (GroovyObject) groovy.newInstance();
						Memory.getGroovyContent().put(FilenameUtils.getBaseName(gFile.getName()),
								FileUtils.readFileToString(gFile, Charset.defaultCharset()));
						Memory.getExtensionsContent().put(FilenameUtils.getBaseName(gFile.getName()),
								FileUtils.readFileToString(gFile, Charset.defaultCharset()));
						Memory.getGroovyObjects().put(FilenameUtils.getBaseName(gFile.getName()), groovyObj);
						Memory.getExtensionObjects().put(FilenameUtils.getBaseName(gFile.getName()), groovyObj);
						System.out.println("INSIDE GROOVY CLASSS**************************");

					} catch (InstantiationException | IllegalAccessException e) {
						log.error("Error while groovy extensions invocation ", e);
					}

				} catch (CompilationFailedException | IOException e) {
					log.error("Error while groovy extensions registration ", e);
				}

			}
		});

	}

}