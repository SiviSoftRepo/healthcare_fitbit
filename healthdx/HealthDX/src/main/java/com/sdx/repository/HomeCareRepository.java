/*
* Java bean class
* Created on 2020-02-08 ( Date ISO 2020-02-08 - Time 19:52:10 )
* Generated by Telosys Tools Generator ( version 3.1.2 )
*/

package com.sdx.repository;

import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.sdx.entity.HomeCare;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.sdx.entity.BaseEntity;
import com.sdx.entity.Diagnosis;
import com.sdx.entity.Doctor;

/**
 * Repository
 *
 * @author Telosys Tools Generator
 *
 */
@Repository
public interface HomeCareRepository
		extends PagingAndSortingRepository<HomeCare, String>, MongoRepository<HomeCare, String> {
	@Query("{'_id' :?0}")
	BaseEntity findByHomeCareId(String id);


	@Query(value = "{'name' :?0 }", fields = "{'_id' :1,'idRep': 1, 'diseasesName':1 }")
	HomeCare findByHomecareName(String name);

}
