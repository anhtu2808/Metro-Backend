package com.metro.user.event;

public interface EventBuilder {
    void buildOtpEvent(String email, String username, String otp);
    void buildWelcomeEvent(String email, String username);
}
