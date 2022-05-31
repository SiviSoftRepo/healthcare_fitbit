package com.sdx.eventtypes;

public class ResourceAdditionEvent extends HCBaseEvent {
	
	private Object addedSubject		= null;
	private Class<?> typeIndicator	= null;

	public Object getAddedSubject() {
		return addedSubject;
	}

	public void setAddedSubject(Object addedSubject) {
		this.addedSubject = addedSubject;
	}

	public Class<?> getTypeIndicator() {
		return typeIndicator;
	}

	public void setTypeIndicator(Class<?> typeIndicator) {
		this.typeIndicator = typeIndicator;
	} 

}
