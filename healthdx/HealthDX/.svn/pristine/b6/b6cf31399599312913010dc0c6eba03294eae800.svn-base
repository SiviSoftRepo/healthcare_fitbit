package com.sdx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sdx.entity.BaseEntity;
import com.sdx.entity.Diagnosis;
import com.sdx.entity.Doctor;

@Repository
public interface DiagnosisRepository
		extends PagingAndSortingRepository<Diagnosis, String>, MongoRepository<Diagnosis, String> {
	
	@Query("{'_id' :?0}")
	BaseEntity findByDiagnosisId(String id);
	
	@Query(value = "{'diseasesName' :?0 }", fields = "{'_id' :1,'idRep': 1, 'diseasesName':1 }")
	Diagnosis findByDiagnosisName(String diseasesName);
}
