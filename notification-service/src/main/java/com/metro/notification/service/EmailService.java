package com.metro.notification.service;

import com.metro.notification.dto.request.SendEmailRequest;
import com.metro.notification.dto.response.EmailResponse;

public interface EmailService {
    EmailResponse sendEmail(SendEmailRequest request);
}
