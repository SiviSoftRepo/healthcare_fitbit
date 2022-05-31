package com.sdx.eventHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;


public class AnonymousEventHandler implements UpdateListener {
	

    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousEventHandler.class);


	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {

		System.out.println("\n\n "+newEvents+", "+newEvents);
		LOGGER.info("Found Annonymous Events: {}", newEvents.length);

    	int counter = 0;
    	
        if (newEvents!= null  && newEvents.length > 0) {
        	for (EventBean eb : newEvents) {
        		counter++;
        		System.out.println("["+counter+"] NEW underlying ::: "+eb.getUnderlying());
        	}
        }
        
    	counter = 0;
    	if (oldEvents!= null  && oldEvents.length > 0) {
    		for (EventBean eb : oldEvents) {
        		counter++;
        		System.out.println("["+counter+"] OLD underlying ::: "+eb.getUnderlying());
        	}
    	}
	
	}

}
