package com.sdx.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sdx.entity.BaseEntity;
import com.sdx.entity.Medic;

@Repository
public interface MedicRepository extends PagingAndSortingRepository<Medic, String>, MongoRepository<Medic, String> {

	@Query("{'_id' :?0}")
	BaseEntity findByMedicId(String id);

}
