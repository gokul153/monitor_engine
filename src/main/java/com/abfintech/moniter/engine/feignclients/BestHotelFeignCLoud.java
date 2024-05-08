package com.abfintech.moniter.engine.feignclients;

import com.abfintech.moniter.engine.controller.abrail.StoreRequestController;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "best-hotels",url = "${feign.client.best-hotel.api.url}")
public interface BestHotelFeignCLoud {
    @GetMapping("/hotels")
    List<StoreRequestController> getBestHotelBydetail(@RequestParam String city , @RequestParam String fromDate, @RequestParam String toDate, @RequestParam String noOfAdults);
}
