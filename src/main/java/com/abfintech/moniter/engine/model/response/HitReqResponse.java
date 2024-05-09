package com.abfintech.moniter.engine.model.response;

import com.abfintech.moniter.engine.model.entity.ResponseLogEntity;
import lombok.Data;

import java.util.List;

@Data
public class HitReqResponse {
    private List<ResponseLogEntity> responses;
    private double percentage;
}
