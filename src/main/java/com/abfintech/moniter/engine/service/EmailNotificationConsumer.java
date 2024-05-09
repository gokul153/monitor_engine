package com.abfintech.moniter.engine.service;

import com.abfintech.moniter.engine.model.DTO.NotificationDTO;
import com.abfintech.moniter.engine.model.entity.ResponseLogEntity;
import com.abfintech.moniter.engine.model.entity.ResponseModelEntity;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailNotificationConsumer {


    @Autowired
    private JavaMailSender emailSender;


    public void receiveNotification(NotificationDTO notification) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


//        SimpleMailMessage message = new SimpleMailMessage();

            helper.setTo(notification.getEmail());

            helper.setSubject("Error detected in your service " + notification.getServiceName());
            helper.setText(
                    "Error detected in your service " +
                            "\n\t\tService Name: " + notification.getServiceName() +
                            "\n\t\tRequest time: " + notification.getTime());

            pdfGenerator(notification.getServiceName(), notification.getResponses(), outputStream);
            ByteArrayResource pdfAttachment = new ByteArrayResource(outputStream.toByteArray());
            helper.addAttachment("Impact Monitor Report " + LocalDateTime.now()+".pdf", pdfAttachment);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // Process the received notification
    }

    private void pdfGenerator(String serviceName, List<ResponseLogEntity> responses, ByteArrayOutputStream pdfOutputStream) {

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream stream = new PDPageContentStream(document, page)) {
                // Set font size for the heading
                stream.setFont(PDType1Font.HELVETICA_BOLD, 36);

                // Add blue background for the header
                stream.setNonStrokingColor(0, 0, 255); // Blue color
                stream.fillRect(0, 700, 595, 60); // Adjust the dimensions as needed

                // Set font color to white
                stream.setNonStrokingColor(255, 255, 255); // White color

                // Add the heading text
                stream.beginText();
                stream.newLineAtOffset(100, 720); // Adjust the coordinates
                stream.showText("Impact Monitor Report"); // Set the heading text
                stream.endText();

                // Reset font size and color for the rest of the content
                stream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                stream.setNonStrokingColor(0, 0, 0); // Black color

                // Add the remaining content
                stream.beginText();
                stream.newLineAtOffset(100, 660); // Adjust the coordinates
                stream.showText("Service name: " + serviceName);
                responses.forEach(response -> {
                    try {
                        stream.newLine();
                        stream.newLineAtOffset(0, -20);
                        stream.newLineAtOffset(0, -20);
                        stream.showText("Request name: " + response.getRequestName());
                        List<String> wrappedErrorMessageLines = splitText(response.getResponse().toString(), PDType1Font.HELVETICA, 14, 595 - 2 * 100);
                        for (String line : wrappedErrorMessageLines) {
                            stream.newLineAtOffset(0, -20); // Move to the next line
                            stream.showText(line);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                stream.endText();
            }

            document.save(pdfOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<String> splitText(String text, PDFont font, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float width = font.getStringWidth(subString) / 1000 * fontSize;
            if (width > maxWidth) {
                if (lastSpace < 0)
                    lastSpace = spaceIndex;
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        return lines;
    }

}
