package com.metro.order.exception;


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
    USER_NOT_FOUND(1008, "User ID is required for creating a ticket order.", HttpStatus.NOT_FOUND),
    TICKET_TYPE_NOT_FOUND(1009, "Ticket type ID is required for creating a ticket order.", HttpStatus.NOT_FOUND),
    START_STATION_NOT_FOUND(1010, "Start station ID is required for creating a ticket order.", HttpStatus.NOT_FOUND),
    END_STATION_NOT_FOUND(1011, "End station ID is required for creating a ticket order.", HttpStatus.NOT_FOUND),
    DYNAMIC_PRICE_NOT_FOUND(1012, "Dynamic price not found for the given line and stations.", HttpStatus.NOT_FOUND),
    INVALID_STATION_COMBINATION(1019, "Invalid station combination for the ticket order.", HttpStatus.BAD_REQUEST),
    TICKET_ORDER_NOT_FOUND(1020, "Ticket order not found", HttpStatus.NOT_FOUND),
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
