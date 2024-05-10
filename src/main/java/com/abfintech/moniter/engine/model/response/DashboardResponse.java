package com.abfintech.moniter.engine.model.response;

import lombok.Data;

import java.util.List;

@Data
public class DashboardResponse {
    List<DashResponse> dashResponses;
}
