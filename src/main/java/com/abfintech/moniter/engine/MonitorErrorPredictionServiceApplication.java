package com.abfintech.moniter.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class MonitorErrorPredictionServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(MonitorErrorPredictionServiceApplication.class, args);
	}

}

