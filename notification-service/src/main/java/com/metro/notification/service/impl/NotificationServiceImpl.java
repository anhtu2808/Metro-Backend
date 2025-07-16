package com.metro.notification.service.impl;

import com.metro.event.dto.NotificationEvent;
import com.metro.notification.dto.request.Recipient;
import com.metro.notification.dto.request.SendEmailRequest;
import com.metro.notification.service.EmailService;
import com.metro.notification.service.NotificationService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    EmailService emailService;
    SpringTemplateEngine templateEngine;

    @Override
    public void sendEmailNotification(NotificationEvent event) {
        try {
            String templateCode = event.getTemplateCode() != null ? event.getTemplateCode() : "welcome-email";
            String htmlContent = renderEmailTemplate(event, templateCode);
            SendEmailRequest request = SendEmailRequest.builder()
                    .to(Recipient.builder()
                            .email(event.getRecipient())
                            .name(event.getParam() != null && event.getParam().containsKey("userName")
                                    ? event.getParam().get("userName").toString()
                                    : "User")
                            .build())
                    .subject(event.getSubject() != null ? event.getSubject() : "Notification from Metro")
                    .htmlContent(htmlContent)
                    .build();

            emailService.sendEmail(request);
            log.info("Email sent successfully to: {}", event.getRecipient());
        } catch (Exception e) {
            log.error("Failed to send email notification to {}: {}", event.getRecipient(), e.getMessage());
        }
    }
    private String renderEmailTemplate(NotificationEvent event, String templateCode) {
        Context context = new Context();
        Map<String, Object> params = event.getParam() != null ? new HashMap<>(event.getParam()) : new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        context.setVariable("email", params.getOrDefault("email", event.getRecipient()));
        return templateEngine.process(templateCode, context);
    }
}
