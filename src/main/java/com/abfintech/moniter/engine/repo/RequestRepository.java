package com.abfintech.moniter.engine.repo;

import com.abfintech.moniter.engine.model.entity.RequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends MongoRepository<RequestEntity,String> {
    List<RequestEntity> findByImpactService(String impactService);
}

