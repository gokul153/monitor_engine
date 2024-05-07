package com.abfintech.moniter.engine.repo;

import com.abfintech.moniter.engine.model.entity.ResponseLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ResponseStoreRepository extends MongoRepository<ResponseLogEntity,String> {

}
