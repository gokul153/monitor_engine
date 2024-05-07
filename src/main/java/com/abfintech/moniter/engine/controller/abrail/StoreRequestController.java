package com.abfintech.moniter.engine.controller.abrail;

import com.abfintech.moniter.engine.model.request.AddRequestDTO;
import com.abfintech.moniter.engine.service.DataAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Api(value = "Enter request")
@RestController
@RequestMapping("/entry")
public class StoreRequestController {

    @Autowired
    private DataAddService dataAddService;

    @PostMapping("/request")
    public ResponseEntity<String> saveRequest(@RequestBody AddRequestDTO requestDTO) {
        dataAddService.saveRequestAndResponse(requestDTO);
        return ResponseEntity.ok("Request saved successfully");
    }

}
