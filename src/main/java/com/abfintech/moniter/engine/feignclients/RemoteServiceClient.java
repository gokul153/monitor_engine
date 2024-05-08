package com.abfintech.moniter.engine.feignclients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@FeignClient(name="ab-monitor-prediction-service")
public interface  RemoteServiceClient {

    @PostMapping
    ResponseEntity<Object> sendPostRequest(URI url, @RequestBody Object requestBody, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);

    @GetMapping
    ResponseEntity<Object> sendGetRequest(URI url, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);

    @PutMapping
    String sendPutRequest(URI url, @RequestBody Object requestBody, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);

    @DeleteMapping
    String sendDeleteRequest(URI url, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);
}
