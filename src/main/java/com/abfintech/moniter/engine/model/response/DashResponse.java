package com.abfintech.moniter.engine.model.response;

import lombok.Data;

@Data
public class DashResponse {
    private String serviceName;
    private double percentage;
}
