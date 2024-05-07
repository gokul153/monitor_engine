package com.abfintech.moniter.engine.repo;

import com.abfintech.moniter.engine.model.entity.ResponseModelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseModelRepository extends MongoRepository<ResponseModelEntity,String> {

}
