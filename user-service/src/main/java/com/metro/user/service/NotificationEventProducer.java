package com.metro.user.service;

import com.metro.event.dto.NotificationEvent;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Supplier;

public interface NotificationEventProducer {
    void sendWelcomeEmailEvent(NotificationEvent event);
    }
