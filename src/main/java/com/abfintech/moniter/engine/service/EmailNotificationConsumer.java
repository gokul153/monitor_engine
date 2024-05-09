package com.abfintech.moniter.engine.service;

import com.abfintech.moniter.engine.model.DTO.NotificationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationConsumer {


    @Autowired
    private JavaMailSender emailSender;



    public void receiveNotification(NotificationDTO notification) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getEmailId());
        message.setSubject("Your Event Booking is Success: "+notification.getEventName());
        message.setText("Dear "+notification.getNameOfBooker()+",\n"+
                "Your Event Booking is successful. Find the details below: \n"+
                "\n\t\tEvent Name: "+notification.getEventName()+
                "\n\t\tEvent Date: "+notification.getEventDate()+
                "\n\t\tBooking ID: "+notification.getBookingId()+
                "\n\t\tNumber of Tickets: "+notification.getNumberOfTickets());
        emailSender.send(message);

        // Process the received notification
    }
}
