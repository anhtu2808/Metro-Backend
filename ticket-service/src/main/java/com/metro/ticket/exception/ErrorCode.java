package com.metro.ticket.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    DYNAMIC_PRICE_MASTER_EXISTS(2001, "Dynamic price master already exists for this line", HttpStatus.BAD_REQUEST),
    DYNAMIC_PRICE_MASTER_NOT_FOUND(2001, "Dynamic price rule not found", HttpStatus.NOT_FOUND),
    LINE_SEGMENT_NOT_FOUND(2002, "Line segments not found", HttpStatus.NOT_FOUND),
    DYNAMIC_PRICE_NOT_FOUND(2002, "Dynamic price not found", HttpStatus.NOT_FOUND ),
    INVALID_REQUEST(3001, "Invalid request", HttpStatus.BAD_REQUEST),
    INVALID_TICKET_PRICE(3002, "Ticket price must be greater than zero", HttpStatus.BAD_REQUEST),
    INVALID_TICKET_NAME(3003, "Ticket name cannot be empty", HttpStatus.BAD_REQUEST),
    TICKET_NAME_ALREADY_EXISTS(3004, "Ticket name already exists", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

