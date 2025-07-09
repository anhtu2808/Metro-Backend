package com.metro.user.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(404, "Role not found", HttpStatus.NOT_FOUND),
    STUDENT_USER_NOT_FOUND(1009, "User ID must be provided for Student Verification.", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(1010, "Permission already existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1011, "Permission not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1012, "Role already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1013, "User not found", HttpStatus.NOT_FOUND),
    INCORRECT_USERNAME_PASSWORD(1009, "Incorrect username or password", HttpStatus.UNAUTHORIZED),
    EMAIL_EXISTED(1014, "Email already existed", HttpStatus.BAD_REQUEST),
    INVALID_OTP(1015, "Invalid OTP code", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1016, "OTP code has expired", HttpStatus.BAD_REQUEST),
    NOTIFICATION_SEND_FAILED(1015, "Failed to send notification", HttpStatus.INTERNAL_SERVER_ERROR);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
