package com.sdx.eventtypes;

public class LoginEvent extends HCBaseEvent {
	
	private Class<?> usrType	= null;

	public Class<?> getUsrType() {
		return usrType;
	}

	public void setUsrType(Class<?> usrType) {
		this.usrType = usrType;
	}

}
