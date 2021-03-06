
/*
 * Java bean class
 * Created on 2020-02-08 ( Date ISO 2020-02-08 - Time 19:52:09 )
 * Generated by Telosys Tools Generator ( version 3.1.2 )
 */

package com.sdx.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;
import com.sdx.entity.BaseEntity;
import org.bson.types.ObjectId;

@Document(collection = "eventDistributor")
public class EventDistributor implements Serializable, BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	public ObjectId _id;
	private String idRep;
	private String patientName;
	private String diagnosisName;
	private String vitalName;
	private String testResult;
	private String testStatus;
	private String eventStatus;
	private String lowerLimit;
	private String upperLimit;
	private String suffix;
	private String state;
	
	private long datetime = 0;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private String diseasesName;

	

	public EventDistributor() {

		super();
	}

	public EventDistributor(String id) {
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

	

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDiagnosisName() {
		return diagnosisName;
	}

	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}

	public String getVitalName() {
		return vitalName;
	}

	public void setVitalName(String vitalName) {
		this.vitalName = vitalName;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getTestStatus() {
		return testStatus;
	}

	public String getDiseasesName() {
		return diseasesName;
	}

	public void setDiseasesName(String diseasesName) {
		this.diseasesName = diseasesName;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	
	public String getLowerLimit() {
		return lowerLimit;
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
		
		
		sb.append("|patientName"); sb.append(patientName); sb.append("|diagnosisName"); sb.append(diagnosisName);
		sb.append("|vitalName"); sb.append(vitalName); sb.append("|upperLimit"); sb.append(upperLimit);
		sb.append("|lowerLimit"); sb.append(lowerLimit); sb.append("|diseasesName"); sb.append(diseasesName);
		sb.append("|testStatus"); sb.append(testStatus);
		sb.append("|state"); sb.append(state);
		return sb.toString();
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}


}