package com.abfintech.moniter.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication(scanBasePackages = {

		"com.arabbank.core.configuration.cqrs",

		"com.arabbank.core.configuration.integration",

		"com.arabbank.core.configuration.web",

		"com.arabbank.core.configuration.cor",

		"com.arabbank.core.model.dto",

		"com.arabbank.core.utils.CommonContext",

		"com.arabbank.feign.client.configuration",

		"com.abfintech.moniter.engine"

})
@EnableFeignClients
public class MonitorErrorPredictionServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(MonitorErrorPredictionServiceApplication.class, args);
	}

}

