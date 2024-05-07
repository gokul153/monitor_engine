package com.abfintech.moniter.engine.feignclients;


import com.arabbank.marketplace.common.lib.feign.CustomMicroserviceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@FeignClient(name="ab-monitor-prediction-service")
public interface  RemoteServiceClient {

    @PostMapping
    String sendPostRequest(URI url, @RequestBody String requestBody, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);

    @GetMapping
    String sendGetRequest(URI url, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);

    @PutMapping
    String sendPutRequest(URI url, @RequestBody String requestBody, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);

    @DeleteMapping
    String sendDeleteRequest(URI url, @RequestHeader Map<String, String> headers, @RequestParam Map<String, Object> params);
}
