package com.sdx.platform.quartz;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.control.CompilationFailedException;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.internal.event.json.core.JsonEventType;
import com.espertech.esper.common.internal.event.map.MapEventBean;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.espertech.esper.runtime.client.scopetest.SupportUpdateListener;
import com.sdx.platform.config.Memory;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;


import static com.sdx.platform.util.EPLCompileUtil.compileDeploy;

@Slf4j
public class DefaultActions {

	public static void init() {
		//registerAllSensorEvents();
		//registerStatements();
		registerGroovies();
		System.out.println("Registered Components "+Memory.getGLOBAL_CONTEXT().getComponentNames());
		try {
			Memory.getGLOBAL_CONTEXT().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static Map<String, Object> testWindow() {
		EPStatement stmt = Memory.getCEP_ENGINE().getDeploymentService().getStatement("SensorPlayground", "sensor window");
		System.out.println("stmt "+stmt);
        Map<String, Object> result = new LinkedHashMap<String, Object>();
    	int resultcounter = 0;
        for (Iterator<EventBean> it = stmt.iterator(); it.hasNext(); ) {
            EventBean next = it.next();
        	System.out.println("\nYEAH, Here -- "+next.getClass()+", then UL: "+next.getUnderlying());
        	if (next instanceof MapEventBean) {
        		resultcounter++;
				MapEventBean meb = (MapEventBean) next;
				System.out.println("YES, right :::: "+meb.getProperties().keySet());
				for (String key : meb.getProperties().keySet()) {
					System.out.println("\t"+key+" -> "+meb.getProperties().get(key).getClass()+" -> "+meb.getProperties().get(key));
					result.put("Answer"+resultcounter, meb);
				}
			}
        	
        }
        System.out.println("It's done ["+resultcounter+"] "+result);
        return result;
	}
	
	private static void registerAllSensorEvents() {
		try {
			String content = FileUtils.readFileToString(new File("D:\\sensorQueries.epl"), Charset.defaultCharset());
			EPDeployment epDep = compileDeploy(Memory.getCEP_ENGINE(), content, "SensorPlayground");
			
			System.out.println("epDep "+epDep);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void registerStatements() {
		
		//Good query
		//String expression = "@Name('sensor-temp-event') insert into SensorEventWindow  select * from SensorEvent where ((cast(temperature?, int) > 20) and (cast(entityName?.english, string) like '%Water%'))";
		//String newExp = "select entityID? as entityId, temperature? as temperature, status? as status, entityName? as entityName, vt? as vt, flags? as flags from SensorEvent";
		
		
		String windowExpression = "@Name('sensor window') create window SensorEventWindow#keepall() as SensorEvent";
		EPDeployment epDep = compileDeploy(Memory.getCEP_ENGINE(), windowExpression, "SensorPlayground");
		
		
		windowExpression = "@Name('OnMatch-Sensor') on SensorEvent se insert into SensorEventWindow select * from SensorEvent where ((cast(temperature?, int) > 20))";
		epDep = compileDeploy(Memory.getCEP_ENGINE(), windowExpression);
		
		
		/*
		 * EPStatement stmt = epDep.getStatements()[0]; stmt.addListener(new
		 * UpdateListener() {
		 * 
		 * @Override public void update(EventBean[] newEvents, EventBean[] oldEvents,
		 * EPStatement statement, EPRuntime runtime) {
		 * System.out.println("SELECT from WINDOW newEvents "+newEvents); if
		 * (newEvents!=null && newEvents.length > 0) { for (EventBean eb : newEvents) {
		 * System.out.println("NEW eb "+eb+" ul["+eb.getUnderlying().getClass()+"] -> "
		 * +eb.getUnderlying()); if (eb instanceof MapEventBean) { MapEventBean
		 * mapEveBean = (MapEventBean) eb;
		 * System.out.println("mapEveBean ["+mapEveBean+"] "+mapEveBean.getUnderlying())
		 * ; } } } if (oldEvents!=null && oldEvents.length > 0) { for (EventBean eb :
		 * oldEvents) {
		 * System.out.println("OLD eb "+eb+" ul["+eb.getUnderlying().getClass()+"] -> "
		 * +eb.getUnderlying()); if (eb instanceof MapEventBean) { MapEventBean
		 * mapEveBean = (MapEventBean) eb;
		 * System.out.println("mapEveBean ["+mapEveBean+"] "+mapEveBean.getUnderlying())
		 * ; } } } } });
		 */
		
		
		/*
		 * String expression =
		 * "insert into SensorEventWindow select * from SensorEvent "; epDep =
		 * compileDeploy(Memory.getCEP_ENGINE(), expression); EPStatement stmt =
		 * epDep.getStatements()[0]; stmt.addListener(new UpdateListener() {
		 * 
		 * @Override public void update(EventBean[] newEvents, EventBean[] oldEvents,
		 * EPStatement statement, EPRuntime runtime) {
		 * System.out.println("insert to WINDOW newEvents "+newEvents); if
		 * (newEvents!=null && newEvents.length > 0) { for (EventBean eb : newEvents) {
		 * System.out.println("eb "+eb+" ul["+eb.getUnderlying().getClass()+"] -> "+eb.
		 * getUnderlying()); if (eb instanceof MapEventBean) { MapEventBean mapEveBean =
		 * (MapEventBean) eb;
		 * System.out.println("mapEveBean ["+mapEveBean+"] "+mapEveBean.getUnderlying())
		 * ; } }
		 * 
		 * } } });
		 */
		
		/*
		 * expression =
		 * "@Name('sensor-temp-event-fromWINDOW') select * from SensorEventWindow";
		 * epDep = compileDeploy(Memory.getCEP_ENGINE(), windowExpression);
		 * 
		 */
		
		
		/*
		 * stmt.addListener(new UpdateListener() { public void update(EventBean[]
		 * newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
		 * System.out.println("\n");
		 * System.out.println("newEvents "+Arrays.toString(newEvents));
		 * System.out.println("oldEvents "+Arrays.toString(oldEvents)); for (EventBean
		 * eb: newEvents) {
		 * System.out.println(eb.getUnderlying().getClass()+" -> "+eb.getUnderlying().
		 * toString()); }
		 * 
		 * } });
		 */
		
		
		System.out.println("*****************************************************************************");
		System.out.println("All statements are Registered");
	}
	

	private static void registerGroovies() {
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