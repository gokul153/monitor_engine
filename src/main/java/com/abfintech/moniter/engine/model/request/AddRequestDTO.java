package com.abfintech.moniter.engine.model.request;

import com.abfintech.moniter.engine.model.entity.ResponseModelEntity;
import com.abfintech.moniter.engine.model.enums.RequestTypeEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AddRequestDTO {
    private String parentService;
    private String requestBody;
    private String requestName;
    private String impactService;
    private String url;
    Map<String,Object> params;
    Map<String,String>  headers;
    private RequestTypeEnum requestType;
    List<ResponseModelEntity> responses;
}
