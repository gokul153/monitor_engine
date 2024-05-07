package com.abfintech.moniter.engine.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModelEntity {
    @Id
    private String id;
    private String requestId;
    private Object response;
    private String impactService;
    private String requestName;
    private String rootCause;
    private String informEmail;
    private String responseCode;
}
