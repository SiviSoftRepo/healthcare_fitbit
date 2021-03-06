/*
* Java bean class
* Created on 2020-02-08 ( Date ISO 2020-02-08 - Time 20:02:08 )
* Generated by Telosys Tools Generator ( version 3.1.2 )
*/

package com.sdx.repository;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.sdx.entity.Telemedicine;
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
public interface TelemedicineRepository extends PagingAndSortingRepository<Telemedicine, String>,MongoRepository<Telemedicine, String>
{
@Query("{'_id' :?0}")
BaseEntity findByTelemedicineId(String id);
}
