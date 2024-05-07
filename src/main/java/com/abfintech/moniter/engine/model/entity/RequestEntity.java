package com.abfintech.moniter.engine.model.entity;

import com.abfintech.moniter.engine.model.enums.RequestTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {
    @Id
    private String id;
    private String requestId;
    private String parentService;
    private String requestBody;
    private String requestName;
    private String impactService;
    private String url;
    private LocalDateTime timestamp;
    private Map<String,Object>  params;
    private Map<String,String>  headers;
    private RequestTypeEnum requestType;
}
