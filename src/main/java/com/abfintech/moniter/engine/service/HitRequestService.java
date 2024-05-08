package com.abfintech.moniter.engine.service;

//import com.abfintech.moniter.engine.feignclients.RemoteServiceClient;
import com.abfintech.moniter.engine.feignclients.BestHotelFeignCLoud;
import com.abfintech.moniter.engine.feignclients.RemoteServiceClient;
import com.abfintech.moniter.engine.model.entity.ResponseLogEntity;
import com.abfintech.moniter.engine.model.entity.RequestEntity;
import com.abfintech.moniter.engine.repo.RequestRepository;
import com.abfintech.moniter.engine.repo.ResponseModelRepository;
import com.abfintech.moniter.engine.repo.ResponseStoreRepository;

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

//                    response = remoteServiceClient.sendPostRequest(new URI("http://localhost:9191/crazyhotel/newentry"), requestBody, headers);
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

    public Void hitTargetService(String impactService) {

        List<RequestEntity> requestEntityList = requestRepository.findByImpactService(impactService);
        if (!requestEntityList.isEmpty()) {
            requestEntityList.forEach(request ->{
                switch (request.getRequestType()){
                    case POST:
                        ResponseEntity<Object> response = null;
                        try {
                            response = remoteServiceClient.sendPostRequest(new URI(request.getUrl()), request.getRequestBody(), request.getHeaders(), request.getParams());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        ResponseLogEntity responseLogEntity = new ResponseLogEntity();
                        responseLogEntity.setResponse(response.getBody());
                     //   responseLogEntity.setStatusCode(response.getStatusCode());
                        responseLogEntity.setTimestamp(LocalDateTime.now());

                        responseStoreRepository.save(responseLogEntity);
                        break;
                    case GET:
                        try {
                            ResponseEntity<Object> response1 = remoteServiceClient.sendGetRequest(new URI(request.getUrl()),  request.getHeaders(), request.getParams());
                            ResponseLogEntity responseLogEntity1 = new ResponseLogEntity();
                            responseLogEntity1.setResponse(response1.getBody());
//                            responseLogEntity.setStatusCode(response.getStatusCode().getReasonPhrase());
                            responseLogEntity1.setTimestamp(LocalDateTime.now());
                            responseStoreRepository.save(responseLogEntity1);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PUT:
                        try {
                            remoteServiceClient.sendPutRequest(new URI(request.getUrl()), request.getRequestBody(),  request.getHeaders(), request.getParams());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        break;
                    case DELETE:
                        try {
                            remoteServiceClient.sendDeleteRequest(new URI(request.getUrl()), request.getHeaders(), request.getParams());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            });
        }
        return null;
    }


}
