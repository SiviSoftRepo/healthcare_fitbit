package com.sdx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sdx.entity.BaseEntity;
import com.sdx.entity.HomeCare;

import com.sdx.entity.Vitals;

@Repository
public interface VitalsRepository
		extends PagingAndSortingRepository<Vitals, String>, MongoRepository<Vitals, String> {

	@Query("{'_id' :?0}")
	BaseEntity findByThersoldId(String id);
	
	@Query(value = "{'name' :?0 }", fields = "{'_id' :1,'idRep': 1, 'name':1,'lowerLimit':1,'upperLimit':1,'suffix':1 }")
	Vitals findByDiseaseName(String diseaseName);
}
