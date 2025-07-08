package com.metro.user.service.impl;

import com.metro.event.dto.NotificationEvent;
import com.metro.user.service.NotificationEventProducer;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NotificationEventProducerImpl implements NotificationEventProducer {

    StreamBridge streamBridge;

    public NotificationEventProducerImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void sendWelcomeEmailEvent(NotificationEvent event)
    {
        sendEvent("sendWelcomeEmail-out-0", event);
    }

    private void sendEvent(String bindingName, NotificationEvent event) {
        log.info("Sending event to binding {}: {}", bindingName, event);
        try {
            boolean sent = streamBridge.send(bindingName, event);
            if (sent) {
                log.info("Successfully sent event to binding {}", bindingName);
            } else {
                log.error("Failed to send event to binding {}", bindingName);
                throw new RuntimeException("Failed to send event to binding " + bindingName);
            }
        } catch (Exception e) {
            log.error("Error sending event to binding {}: {}", bindingName, e.getMessage());
            throw new RuntimeException("Error sending event to binding " + bindingName, e);
        }
    }
}