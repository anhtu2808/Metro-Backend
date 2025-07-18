package com.metro.user.service;

import com.metro.event.dto.NotificationEvent;


public interface NotificationEventProducer {
    void sendWelcomeEmailEvent(NotificationEvent event);
    void sendOtpToEmail(NotificationEvent event);
}
