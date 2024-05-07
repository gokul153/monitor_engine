package com.abfintech.moniter.engine.controller;

import com.abfintech.moniter.engine.service.HitRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/hitrequest")
public class CheckPartnerRequestController {
    @Autowired
    HitRequestService hitRequestService;
    @PostMapping("/check")
    public ResponseEntity<String> saveRequest(@RequestParam String impactService) throws URISyntaxException {
        hitRequestService.sendRequestAndSaveResponse(impactService,"Createhotel");
        return ResponseEntity.ok("Request checked successfully");
    }

    @PostMapping("/hit")
    public ResponseEntity<String> hitReq(@RequestParam String impactService) throws URISyntaxException {
        hitRequestService.hitTargetService(impactService);
        return ResponseEntity.ok("Request checked successfully");
    }

}
