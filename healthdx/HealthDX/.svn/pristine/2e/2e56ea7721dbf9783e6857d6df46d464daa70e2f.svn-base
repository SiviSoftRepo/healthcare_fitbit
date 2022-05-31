package com.sdx.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "diagnosis")
public class Diagnosis implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	public ObjectId _id;

	private String idRep;
	private String diseasesName;
	private String specialistName;
	private String doctorIn;
	private String hospitalName;
	
	@DBRef
    private Vitals thresold;
	
	public Vitals getThresold() {
		return thresold;
	}

	public void setThresold(Vitals thresold) {
		this.thresold = thresold;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Diagnosis() {
		super();
	}

	public Diagnosis(String id) {
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

	public String getDiseasesName() {
		return diseasesName;
	}

	public void setDiseasesName(String diseasesName) {
		this.diseasesName = diseasesName;
	}

	public String getSpecialistName() {
		return specialistName;
	}

	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}

	public String getDoctorIn() {
		return doctorIn;
	}

	public void setDoctorIn(String doctorIn) {
		this.doctorIn = doctorIn;
	}

	

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(idRep);
		return sb.toString();
	}

}
