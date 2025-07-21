package com.metro.user.event;

import com.metro.event.dto.NotificationEvent;
import com.metro.user.service.NotificationEventProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventBuilderImpl implements EventBuilder {
    NotificationEventProducer notificationEventProducer;
    @Override
    public void buildOtpEvent(String email, String username, String otp) {
        NotificationEvent event = NotificationEvent.builder()
                .channel("email")
                .recipient(email)
                .templateCode("otp-email")
                .param(Map.of(
                        "otp", otp,
                        "userName", username  // Thêm username để hiển thị trong email
                ))
                .subject("Your OTP Code")
                .body("")
                .build();
        notificationEventProducer.sendOtpToEmail(event);
    }

    @Override
    public void buildWelcomeEvent(String email, String username) {
        try {
            NotificationEvent event = NotificationEvent.builder()
                    .channel("email")
                    .recipient(email)
                    .templateCode("welcome-email")
                    .param(Map.of(
                            "userName", username
                    ))
                    .subject("Chào mừng bạn đến với Metro!")
                    .build();
            notificationEventProducer.sendWelcomeEmailEvent(event);
            log.info("Welcome notification sent for user: {}", username);
        } catch (Exception e) {
            log.error("Failed to send welcome notification for user: {}, error: {}", username, e.getMessage());
        }
    }
}
