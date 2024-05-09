package com.abfintech.moniter.engine.model.DTO;

import lombok.Data;

@Data
public class NotificationDTO  {
    String eventName;
    String eventDate;
    int numberOfTickets;
    String bookingId;
    private String nameOfBooker;
    private String emailId;
}
