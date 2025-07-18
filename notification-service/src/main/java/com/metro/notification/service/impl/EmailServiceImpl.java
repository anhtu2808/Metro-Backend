package com.metro.notification.service.impl;

import com.metro.event.dto.NotificationEvent;
import com.metro.notification.dto.request.EmailRequest;
import com.metro.notification.dto.request.Recipient;
import com.metro.notification.dto.request.SendEmailRequest;
import com.metro.notification.dto.request.Sender;
import com.metro.notification.dto.response.EmailResponse;
import com.metro.notification.exception.AppException;
import com.metro.notification.exception.ErrorCode;
import com.metro.notification.repository.httpClient.EmailClient;
import com.metro.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {
    EmailClient emailClient;

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
}