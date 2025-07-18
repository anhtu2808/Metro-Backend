package com.metro.notification.configuration;

import com.metro.event.dto.NotificationEvent;
import com.metro.notification.service.EmailService;
import com.metro.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class KafkaConsumerConfig {
    NotificationService notificationService;
    @Bean
    public Consumer<NotificationEvent> consumeWelcome() {
        return message -> {
            try {
                if (message == null) {
                    log.warn("Received null message from topic 'metro'");
                    return;
                }
                log.info("📧 Received welcome event from topic 'metro': {}", message.getRecipient());
                notificationService.sendEmailNotification(message);
                log.info("✅ Successfully processed welcome event for: {}", message.getRecipient());
            } catch (Exception e) {
                log.error("❌ Error processing welcome event: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to process event", e);
            }
        };
    }
    @Bean
    public Consumer<NotificationEvent> consumeOtp() {
        return message -> {
            try {
                if (message == null) {
                    log.warn("Received null message from topic 'otp-email'");
                    return;
                }
                log.info("📧 Received OTP event from topic 'otp-email': {}", message.getRecipient());
                emailService.sendEmailNotification(message);
                log.info("✅ Successfully processed OTP event for: {}", message.getRecipient());
            } catch (Exception e) {
                log.error("❌ Error processing OTP event: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to process event", e);
            }
        };
    }
}