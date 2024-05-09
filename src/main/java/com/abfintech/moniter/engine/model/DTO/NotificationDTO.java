package com.abfintech.moniter.engine.model.DTO;

import com.abfintech.moniter.engine.model.entity.ResponseLogEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationDTO  {
    String serviceName;
    LocalDateTime time;
    String email;
    List<ResponseLogEntity> responses;
}
