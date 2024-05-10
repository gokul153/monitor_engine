package com.abfintech.moniter.engine.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DashResponse {
    private String serviceName;
    private double percentage;
    private LocalDateTime timestamp;
}
