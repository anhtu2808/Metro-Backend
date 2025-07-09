package com.metro.notification.service;

import com.metro.event.dto.NotificationEvent;
import com.metro.notification.dto.request.EmailRequest;
import com.metro.notification.dto.request.Recipient;
import com.metro.notification.dto.request.SendEmailRequest;
import com.metro.notification.dto.request.Sender;
import com.metro.notification.dto.response.EmailResponse;
import com.metro.notification.exception.AppException;
import com.metro.notification.exception.ErrorCode;
import com.metro.notification.repository.httpClient.EmailClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailService {
    EmailClient emailClient;
    SpringTemplateEngine templateEngine;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal String apiKey;

    @Retryable(value = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 500))
    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Metro")
                        .email("trankhang0990@gmail.com")
                        .build())
                .to(List.of(Recipient.builder()
                        .email(request.getTo().getEmail())
                        .name(request.getTo().getName())
                        .build()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", request.getTo().getEmail(), e.getMessage());
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
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
            sendEmail(request);
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