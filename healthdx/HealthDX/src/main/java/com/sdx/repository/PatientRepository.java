/*
* Java bean class
* Created on 2020-02-08 ( Date ISO 2020-02-08 - Time 19:52:10 )
* Generated by Telosys Tools Generator ( version 3.1.2 )
*/

package com.sdx.repository;

import java.io.Serializable;


import org.springframework.data.repository.PagingAndSortingRepository;
import com.sdx.entity.Patient;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.sdx.entity.BaseEntity;


/**
 * Repository
 *
 * @author Telosys Tools Generator
 *
 */
@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, String>,MongoRepository<Patient, String>
{
@Query("{'_id' :?0}")
BaseEntity findByPatientId(String id);

@Query(value = "{'name' :?0 }", fields = "{ }")
Patient findByPatientName(String patientName);
}
