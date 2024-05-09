package com.abfintech.moniter.engine.repo;

import com.abfintech.moniter.engine.model.entity.ResponseLogEntity;
import com.abfintech.moniter.engine.model.enums.ResponseType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ResponseStoreRepository extends MongoRepository<ResponseLogEntity,String> {
    List<ResponseLogEntity> findByResponseTypeAndTriggerReference(ResponseType responseType, String triggerReference);
    List<ResponseLogEntity> findByTriggerReference(String triggerReference);

}
