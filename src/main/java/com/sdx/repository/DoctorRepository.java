package com.sdx.repository;

import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.sdx.entity.HomeCare;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.sdx.entity.BaseEntity;
import com.sdx.entity.Doctor;

@Repository
public interface DoctorRepository extends PagingAndSortingRepository<Doctor, String>, MongoRepository<Doctor, String> {
	@Query("{'_id' :?0}")
	BaseEntity findByDoctorId(String id);

	
	@Query(value = "{'doctorName' :?0 }", fields = "{'_id' :1,'idRep': 1, 'doctorName':1 }")
	Doctor findByDoctorName(String doctorName);
}
