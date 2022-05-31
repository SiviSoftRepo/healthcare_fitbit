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
import com.sdx.entity.Vitals;
import com.sdx.repository.HomeCareRepository;
import com.sdx.repository.VitalsRepository;
import com.sdx.service.BaseEntityService;

@Component
@Service
public class VitalsServiceImpl implements BaseEntityService {

	@Autowired
	private VitalsRepository vitalsRepository;

	@Override
	public BaseEntity findById(String id) {

		return this.vitalsRepository.findById(id).get();
	}

	@Override
	public List<BaseEntity> findAll() {
		List<BaseEntity> thres = new ArrayList<>();
		vitalsRepository.findAll().forEach(threshold -> {
			threshold.setIdRep(threshold._id.toString());
			thres.add(threshold);
			System.out.println("FindAll Medic::" + thres);
			
		});
		return thres;
	}
	
	public Vitals getById(String id) {
		Vitals vitals = vitalsRepository.findById(id).orElse(null);
		return vitals;
	}

	@Override
	public BaseEntity save(BaseEntity entity) {
		if (entity instanceof Vitals) {
			Vitals vitals = (Vitals) entity;
			return this.vitalsRepository.save(vitals);
		}
		return null;
	}

	public BaseEntity update(ObjectId _id, BaseEntity entity) {
		if (entity instanceof Vitals) {
			Vitals vitals = (Vitals) entity;
			vitals.set_id(_id);
			return this.vitalsRepository.save(vitals);
		}
		return null;
	}
	

	
	
	
	public ArrayList<Object> findAllDiseaseName() {
		ArrayList<Object> disease = new ArrayList<>();
		vitalsRepository.findAll().forEach(thresoldRep -> {
			disease.add(thresoldRep.getName());
		System.out.println("DOCTOR SERVICE CLASSS "+disease);

		});
		return disease;
	}

	@Override 
	public void delete(String id) {
		vitalsRepository.deleteById(id);
	}

	@Override
	public BaseEntity update(String id, BaseEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Object> findAllVitals() {
		ArrayList<Object> vitals = new ArrayList<>();
		vitalsRepository.findAll().forEach(thresoldRep -> {
		vitals.add(thresoldRep.getName());
		
		System.out.println("Vital SERVICE CLASSS "+vitals);

		});
		return vitals;
	}

	public Vitals findByDiseaseName(String diseaseName) {
		// TODO Auto-generated method stub
		Vitals thersold = (Vitals) this.vitalsRepository.findByDiseaseName(diseaseName);
		System.out.println("ID OF Thresold IS:::::::::" + diseaseName );
		thersold.setIdRep(thersold.get_id().toString());
		System.out.println("Thresold ::::::::::::" + thersold);
		return thersold;
	}
	
	/*
	 * public Vitals findDiseaseName(String diseaseName) { // TODO Auto-generated
	 * method stub Vitals vitals = (Vitals)
	 * this.vitalsRepository.findByDiseaseName(diseaseName);
	 * System.out.println("ID OF Vitals IS:::::::::" + diseaseName );
	 * vitals.setIdRep(vitals.get_id().toString());
	 * System.out.println("Thresold ::::::::::::" + vitals); return vitals; }
	 */
	
	
}
