package com.abfintech.moniter.engine.model.entity;

import com.abfintech.moniter.engine.model.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "response-log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLogEntity {
    private Object response;
    private String statusCode;
    private LocalDateTime timestamp;
    private String triggerReference;
    private ResponseType responseType;
    private String requestName;
    private String serviceName;
}
