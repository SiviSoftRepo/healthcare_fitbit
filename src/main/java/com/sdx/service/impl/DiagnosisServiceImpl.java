package com.sdx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sdx.entity.BaseEntity;
import com.sdx.entity.Diagnosis;
import com.sdx.entity.Doctor;
import com.sdx.entity.HomeCare;
import com.sdx.entity.Patient;
import com.sdx.repository.DiagnosisRepository;
import com.sdx.service.BaseEntityService;

@Component
@Service
public class DiagnosisServiceImpl implements BaseEntityService {

	@Autowired
	private DiagnosisRepository diagnosisRepo;
	
	@Autowired
	private VitalsServiceImpl vitalsRepo;

	@Override
	public BaseEntity findById(String id) {

		System.out.println("Id : " + id);
		return this.diagnosisRepo.findById(id).get();
	}

	public Diagnosis getById(String id) {
		Diagnosis diagnosis = diagnosisRepo.findById(id).orElse(null);
		return diagnosis;
	}

	@Override
	public List<BaseEntity> findAll() {
		System.out.println("FindAll:::::::");
		List<BaseEntity> diagnosis = new ArrayList<>();
		diagnosisRepo.findAll().forEach(care -> {
			care.setIdRep(care._id.toString());
			diagnosis.add(care);

		});
		System.out.println("FindAll HomeCare::" + diagnosis);
		return diagnosis;
	}

	public ArrayList<Object> findAllDoctor() {
		ArrayList<Object> diagnosis = new ArrayList<>();
		diagnosisRepo.findAll().forEach(diagnosisRep -> {
			diagnosis.add(diagnosisRep.getDiseasesName());

		});
		return diagnosis;
	}
	
	
	public BaseEntity saveByRef(BaseEntity entity) {
		if (entity instanceof Diagnosis) {
			Diagnosis diagnosis = (Diagnosis) entity;
			diagnosis.setThresold(vitalsRepo.getById(diagnosis.getThresold().getIdRep()));
			System.out.println("In Service Class"+diagnosis);
			return this.diagnosisRepo.save(diagnosis);
		}
		return null;
	}

	public Diagnosis findByDiagnosisName(String diseasesName) {
		// TODO Auto-generated method stub
		Diagnosis diagnosis = (Diagnosis) this.diagnosisRepo.findByDiagnosisName(diseasesName);
		System.out.println("ID OF Diagnosis IS:::::::::" + diagnosis.get_id().toString());
		diagnosis.setIdRep(diagnosis.get_id().toString());
		System.out.println("diagnosis ::::::::::::" + diagnosis);
		return diagnosis;
	}

	@Override
	public BaseEntity save(BaseEntity entity) {
		if (entity instanceof Diagnosis) {
			Diagnosis diagnosis = (Diagnosis) entity;
			return this.diagnosisRepo.save(diagnosis);
		}
		return null;
	}
	

	public ArrayList<Object> findAllDiagnosis() {
		ArrayList<Object> diagnosis = new ArrayList<>();
		diagnosisRepo.findAll().forEach(diagnosisRep -> {
			diagnosis.add(diagnosisRep.getDiseasesName());
			

		});
		return diagnosis;
	}

	
	/*
	 * public BaseEntity update(ObjectId _id, BaseEntity entity) { if (entity
	 * instanceof Diagnosis) { Diagnosis diagnosis = (Diagnosis) entity;
	 * diagnosis.set_id(_id); return this.diagnosisRepo.save(diagnosis); } return
	 * null; }
	 */
	@Override
	public void delete(String id) {
		diagnosisRepo.deleteById(id);
	}

	@Override
	public BaseEntity update(String id, BaseEntity entity) {
		if (entity instanceof Diagnosis) {
			Diagnosis diagnosis = (Diagnosis) entity;
			diagnosis.setThresold(vitalsRepo.getById(diagnosis.getThresold().getIdRep()));
			System.out.println("Thersold Id>>>>>>> " + diagnosis);
			diagnosis.set_id(new ObjectId(id));
			return this.diagnosisRepo.save(diagnosis);
		}
		return null;
	}


}
