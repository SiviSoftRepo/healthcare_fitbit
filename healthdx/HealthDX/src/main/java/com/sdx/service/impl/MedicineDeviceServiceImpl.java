package com.sdx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sdx.entity.BaseEntity;
import com.sdx.entity.HomeCare;
import com.sdx.entity.MedicineDevice;
import com.sdx.repository.MedicineDeviceRepository;
import com.sdx.service.BaseEntityService;

@Component
@Service
public class MedicineDeviceServiceImpl implements BaseEntityService {

	@Autowired
	private MedicineDeviceRepository medicRepo;

	@Override
	public BaseEntity findById(String id) {
		System.out.println("Id : " + id);
		return this.medicRepo.findById(id).get();
	}

	@Override
	public List<BaseEntity> findAll() {
		List<BaseEntity> medic = new ArrayList<>();
		medicRepo.findAll().forEach(care -> {
			care.setIdRep(care._id.toString());
			medic.add(care);

		});
		System.out.println("FindAll Medic::" + medic);
		return medic;
	}

	@Override
	public BaseEntity save(BaseEntity entity) {
		if (entity instanceof MedicineDevice) {
			MedicineDevice medicineDevice = (MedicineDevice) entity;
			return this.medicRepo.save(medicineDevice);
		}
		return null;
	}

	@Override
	public void delete(String id) {
		medicRepo.deleteById(id);
	}

	public BaseEntity update(ObjectId _id, BaseEntity entity) {
		if (entity instanceof MedicineDevice) {
			MedicineDevice medicineDevice = (MedicineDevice) entity;
			medicineDevice.set_id(_id);
			return this.medicRepo.save(medicineDevice);
		}
		return null;
	}

	@Override
	public BaseEntity update(String id, BaseEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
