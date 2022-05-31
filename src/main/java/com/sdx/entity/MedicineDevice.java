package com.sdx.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teleMedicineDevice")
public class MedicineDevice implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	public ObjectId _id;

	private String idRep;
	private String name;
	private String deviceId;
	private String type;
	private String manufacturer;
	private String serialNo;
	private String caliberated;
	private String caliberation;

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCaliberated() {
		return caliberated;
	}

	public void setCaliberated(String caliberated) {
		this.caliberated = caliberated;
	}

	public String getCaliberation() {
		return caliberation;
	}

	public void setCaliberation(String caliberation) {
		this.caliberation = caliberation;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(idRep);
		return sb.toString();
	}

}
