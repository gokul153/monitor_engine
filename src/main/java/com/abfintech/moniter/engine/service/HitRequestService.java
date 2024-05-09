package com.abfintech.moniter.engine.service;

//import com.abfintech.moniter.engine.feignclients.RemoteServiceClient;

import com.abfintech.moniter.engine.feignclients.BestHotelFeignCLoud;
import com.abfintech.moniter.engine.feignclients.RemoteServiceClient;
import com.abfintech.moniter.engine.model.DTO.NotificationDTO;
import com.abfintech.moniter.engine.model.entity.ResponseLogEntity;
import com.abfintech.moniter.engine.model.entity.RequestEntity;
import com.abfintech.moniter.engine.model.enums.ResponseType;
import com.abfintech.moniter.engine.model.response.HitReqResponse;
import com.abfintech.moniter.engine.repo.RequestRepository;
import com.abfintech.moniter.engine.repo.ResponseModelRepository;
import com.abfintech.moniter.engine.repo.ResponseStoreRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;

@Service

public class HitRequestService {
    @Autowired
    ResponseModelRepository responseModelRepository;
    @Autowired
    ResponseStoreRepository responseStoreRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    BestHotelFeignCLoud bestHotelFeignCLoud;
    @Autowired
    private RemoteServiceClient remoteServiceClient;
    @Autowired
    private EmailNotificationConsumer emailNotificationConsumer;

    public void sendRequestAndSaveResponse(String partner, String requestname) throws URISyntaxException {
        String response = null;
        // Retrieve the entity from MongoDB
        List<RequestEntity> requestEntityList = new ArrayList<>();
        if (requestEntityList != null) {
            for (RequestEntity requestEntity : requestEntityList) {
                // Construct the request body, params, and headers
//                String requestBody = requestEntity.getRequestBody();
                Map<String, String> headers = requestEntity.getHeaders();
                String url = "http://localhost:9191/crazyhotel/newentry";
                // Send the request using Feign Client
                if (Objects.nonNull(headers)) {
                    Map<String, String> headersMap = new HashMap<>();

                   // response = remoteServiceClient.sendPostRequest(new URI("http://localhost:9191/crazyhotel/newentry"), requestBody, headers);
                } else {
                    //      response = remoteServiceClient.sendRequest(url, requestBody);
                }

                if (response != null) {
                    // Save the response in another class
                    ResponseLogEntity responseEntity = new ResponseLogEntity();
                    responseEntity.setTimestamp(LocalDateTime.now());

                    responseEntity.setResponse(response);
                    // Save or process the responseEntity as needed
                    responseStoreRepository.save(responseEntity);
                }
            }

        }
    }

    public HitReqResponse hitTargetService(String impactService) {

        List<RequestEntity> requestEntityList = requestRepository.findByImpactService(impactService);
        List<ResponseLogEntity> responses = new ArrayList<>();
        HitReqResponse hitReqResponse = new HitReqResponse();
        if (!requestEntityList.isEmpty()) {
            String triggerReference = impactService + "-" + RandomStringUtils.randomAlphanumeric(8);
            requestEntityList.forEach(request -> {
                ResponseLogEntity responseLogEntity = new ResponseLogEntity();
                responseLogEntity.setTriggerReference(triggerReference);
                responseLogEntity.setRequestName(request.getRequestName());
                ResponseEntity<Object> response = null;
                switch (request.getRequestType()) {
                    case POST:

                        try {
                            response =  remoteServiceClient.sendPostRequest(new URI("http://localhost:9191/crazyhotel/newentry"), request.getRequestBody(), request.getHeaders());
                            responseLogEntity.setResponse(response.getBody());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.SUCCESS);
                            responseLogEntity.setTriggerReference(triggerReference);
                        } catch (Exception e) {
                            responseLogEntity.setResponse(e.getMessage());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.FAILURE);
                            responseLogEntity.setTriggerReference(triggerReference);
                            e.printStackTrace();
                        }

                        break;
                    case GET:
                        try {
                            response = remoteServiceClient.getAppointments();
                            responseLogEntity.setResponse(response.getBody());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.SUCCESS);
                            responseLogEntity.setTriggerReference(triggerReference);
                        } catch (Exception e) {
                            responseLogEntity.setResponse(e.getMessage());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.FAILURE);
                            responseLogEntity.setTriggerReference(triggerReference);
                            e.printStackTrace();
                        }
                        break;
                    case PUT:
                        try {
                            remoteServiceClient.sendPutRequest(new URI(request.getUrl()), request.getRequestBody(), request.getHeaders(), request.getParams());
                            responseLogEntity.setResponse(response.getBody());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.SUCCESS);
                            responseLogEntity.setTriggerReference(triggerReference);
                        } catch (Exception e) {

                            responseLogEntity.setResponse(e.getMessage());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.FAILURE);
                            responseLogEntity.setTriggerReference(triggerReference);
                            e.printStackTrace();
                        }
                        break;
                    case DELETE:
                        try {
                            remoteServiceClient.sendDeleteRequest(new URI(request.getUrl()), request.getHeaders(), request.getParams());
                            responseLogEntity.setResponse(response.getBody());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.SUCCESS);
                            responseLogEntity.setTriggerReference(triggerReference);
                        } catch (Exception e) {

                            responseLogEntity.setResponse(e.getMessage());
                            responseLogEntity.setTimestamp(LocalDateTime.now());
                            responseLogEntity.setResponseType(ResponseType.FAILURE);
                            responseLogEntity.setTriggerReference(triggerReference);
                            e.printStackTrace();
                        }
                        break;
                }
                responseStoreRepository.save(responseLogEntity);
            });
            List<ResponseLogEntity> responseLogEntities = responseStoreRepository.findByResponseTypeAndTriggerReference(ResponseType.FAILURE, triggerReference);
            responses = responseStoreRepository.findByTriggerReference(triggerReference);
            hitReqResponse.setResponses(responses);
            double c1 = (Double.valueOf(responses.size())-Double.valueOf(responseLogEntities.size())) / Double.valueOf(responses.size());
            hitReqResponse.setPercentage(c1*100);
            if(!responseLogEntities.isEmpty()) {
                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setServiceName(impactService);
                notificationDTO.setEmail(requestEntityList.get(0).getEmail());
                notificationDTO.setTime(LocalDateTime.now());
                notificationDTO.setResponses(responseLogEntities);
                emailNotificationConsumer.receiveNotification(notificationDTO);
            }

        }
        return hitReqResponse;

    }


}
