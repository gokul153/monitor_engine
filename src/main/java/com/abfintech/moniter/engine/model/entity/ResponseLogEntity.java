package com.abfintech.moniter.engine.model.entity;

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
    private String response;
    private LocalDateTime timestamp;
}
