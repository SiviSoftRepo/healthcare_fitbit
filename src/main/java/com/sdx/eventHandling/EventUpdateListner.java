package com.sdx.eventHandling;

import java.io.Serializable;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.internal.event.map.MapEventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;


public class EventUpdateListner implements UpdateListener, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void update(EventBean[] newData, EventBean[] oldData, EPStatement statement, EPRuntime runtime) {
		if (newData!=null && newData.length > 0) {
			for (EventBean eb : newData) {
				if (eb instanceof MapEventBean) {
					MapEventBean new_name = (MapEventBean) eb;
					
				}
			}
			
		}
	}


}
