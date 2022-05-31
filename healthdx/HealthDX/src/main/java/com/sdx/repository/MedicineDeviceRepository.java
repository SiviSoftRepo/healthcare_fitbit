package com.sdx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sdx.entity.BaseEntity;
import com.sdx.entity.MedicineDevice;

@Repository
public interface MedicineDeviceRepository extends PagingAndSortingRepository<MedicineDevice, String>, MongoRepository<MedicineDevice, String> {

	@Query("{'_id' :?0}")
	BaseEntity findByMedicineId(String id);

}
