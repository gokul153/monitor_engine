package com.abfintech.moniter.engine.service;

import com.abfintech.moniter.engine.model.entity.RequestEntity;
import com.abfintech.moniter.engine.model.entity.ResponseModelEntity;
import com.abfintech.moniter.engine.model.request.AddRequestDTO;
import com.abfintech.moniter.engine.repo.RequestRepository;
import com.abfintech.moniter.engine.repo.ResponseModelRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataAddService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ResponseModelRepository responseModelRepository;

    public void saveRequestAndResponse(AddRequestDTO request) {
        RequestEntity requestData = new RequestEntity();
        BeanUtils.copyProperties(request, requestData);
        requestData.setTimestamp(LocalDateTime.now());
        requestData.setRequestId(RandomStringUtils.randomAlphanumeric(8));
        requestRepository.save(requestData);
        request.getResponses().forEach(responseModelEntity -> {
            ResponseModelEntity responseData = new ResponseModelEntity();
            BeanUtils.copyProperties(responseModelEntity, responseData);
            responseData.setRequestId(requestData.getRequestId());
            responseData.setImpactService(requestData.getImpactService());
            responseData.setRequestName(requestData.getRequestName());
            responseModelRepository.save(responseData);
        });


    }


}
