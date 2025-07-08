package com.metro.notification.configuration;

import com.metro.event.dto.NotificationEvent;
import com.metro.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerConfig {
    private final EmailService emailService;
    @Bean
    public Consumer<NotificationEvent> consumeWelcome() {
        return message -> {
            try {
                if (message == null) {
                    log.warn("Received null message from topic 'metro'");
                    return;
                }
                log.info("📧 Received welcome event from topic 'metro': {}", message.getRecipient());
                emailService.sendEmailNotification(message);
                log.info("✅ Successfully processed welcome event for: {}", message.getRecipient());
            } catch (Exception e) {
                log.error("❌ Error processing welcome event: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to process event", e);
            }
        };
    }
}