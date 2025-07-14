package com.metro.route.exception;


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
    BUS_ROUTE_NOT_FOUND(1008, "Bus Route NOT FOUND with given ID", HttpStatus.FORBIDDEN),
    BUS_ROUTE_EXISTED(1009, "Bus Route with given ID already exists", HttpStatus.BAD_REQUEST),
    STATION_NOT_FOUND(1010, "Start station NOT FOUND with given ID", HttpStatus.NOT_FOUND),
    BUS_ROUTE_ID_MISMATCH(1011, "Bus Route ID mismatch", HttpStatus.BAD_REQUEST),
    START_STATION_NOT_FOUND(1012, "Start station does not exist", HttpStatus.NOT_FOUND),
    FINAL_STATION_NOT_FOUND(1013, "Final station does not exist", HttpStatus.NOT_FOUND),
    END_STATION_NOT_FOUND(1014, "End station does not exist", HttpStatus.NOT_FOUND),
    LINE_NOT_FOUND(1015, "Line not found", HttpStatus.NOT_FOUND),
    LINE_SEGMENT_NOT_FOUND(404, "Line segment not found", HttpStatus.NOT_FOUND),
    INVALID_LINE_ID(1016, "Invalid line ID", HttpStatus.BAD_REQUEST),
    INVALID_START_STATION_ID(1017, "Invalid start station ID", HttpStatus.BAD_REQUEST),
    DYNAMIC_PRICE_NOT_FOUND(1018, "Dynamic price not found", HttpStatus.NOT_FOUND),
    INVALID_STATION_COMBINATION(1019, "Line not found for the given station combination", HttpStatus.BAD_REQUEST),
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

