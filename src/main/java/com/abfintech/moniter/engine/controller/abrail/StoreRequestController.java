package com.abfintech.moniter.engine.controller.abrail;

import com.abfintech.moniter.engine.model.request.AddRequestDTO;
import com.abfintech.moniter.engine.service.DataAddService;
import com.abfintech.moniter.engine.service.HitRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

//@Api(value = "Enter request")
@RestController
@RequestMapping("/entry")
public class StoreRequestController {

    @Autowired
    private DataAddService dataAddService;

    @Autowired
    private HitRequestService hitRequestService;

    @PostMapping("/request")
    public ResponseEntity<String> saveRequest(@RequestBody AddRequestDTO requestDTO) {
        dataAddService.saveRequestAndResponse(requestDTO);
        return ResponseEntity.ok("Request saved successfully");
    }

    @GetMapping
    public ResponseEntity<Set<String>> getServices(){
        return ResponseEntity.ok(hitRequestService.getServices());
    }

}
