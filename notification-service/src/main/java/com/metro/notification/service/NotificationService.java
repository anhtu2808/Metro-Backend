package com.metro.notification.service;

import com.metro.event.dto.NotificationEvent;

public interface NotificationService {
    void sendEmailNotification(NotificationEvent event);
}
