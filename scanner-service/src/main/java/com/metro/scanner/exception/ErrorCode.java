package com.metro.scanner.exception;


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
    SIGNER_KEY_NOT_FOUND(500, "Signer key not setup", HttpStatus.INTERNAL_SERVER_ERROR),
    EXPIRED_TOKEN(401, "Mã QR đã hết hạn, vui lòng làm mới!", HttpStatus.BAD_REQUEST),
    TICKET_ORDER_ID_NOT_FOUND(400, "Ticket order ID not found in QR", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(400, "Mã QR không hợp lệ", HttpStatus.BAD_REQUEST),
    TICKET_ORDER_UNPAID(400, "Vé chưa được thanh toán, vui lòng hoàn tất thanh toán", HttpStatus.BAD_REQUEST),
    TICKET_ORDER_EXPIRED(400, "Vé đã hết hạn", HttpStatus.BAD_REQUEST),
    TICKET_ORDER_INACTIVE(400, "Vé chưa được kích hoạt vui lòng kích hoat để sử dụng", HttpStatus.BAD_REQUEST),
    TICKET_STATUS_INVALID(400, "Trạng thái vé không hợp lệ, vui lòng liên hệ nhân viên để nhận hỗ trợ", HttpStatus.BAD_REQUEST),
    INVALID_CHECKIN_STATION(400, "Ga check-in không hợp lệ, vui lòng kiểm tra lại ga check-in", HttpStatus.BAD_REQUEST),
    INVALID_CHECKOUT_STATION(400, "Ga check-out không hợp lệ, vui lòng kiểm tra lại ga check-out", HttpStatus.BAD_REQUEST);


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

