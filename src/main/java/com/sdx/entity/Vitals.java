package com.sdx.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "thersold")
public class Vitals implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	public ObjectId _id;

	private String idRep;
	private String name;
	private String lowerLimit;
	private String upperLimit;
	private String suffix;

	public Vitals() {
		super();
	}
	
	public Vitals(String id) {
		setIdRep(id);
		
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getIdRep() {
		return idRep;
	}

	public void setIdRep(String idRep) {
		this.idRep = idRep;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLowerLimit() {
		return lowerLimit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public String getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(idRep);
		
		return sb.toString();
	}

}
