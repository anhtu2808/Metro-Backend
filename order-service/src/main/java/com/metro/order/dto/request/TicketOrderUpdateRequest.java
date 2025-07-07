package com.metro.order.dto.request;

import com.metro.order.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Yêu cầu cập nhật thông tin đơn hàng vé (Ticket Order) trong hệ thống metro")
public class TicketOrderUpdateRequest {

    @Schema(description = "Trạng thái mới của vé", example = "ACTIVE")
    TicketStatus status;

//    @Positive(message = "Transaction ID must be positive")
//    @Schema(description = "ID của giao dịch thanh toán, nếu cập nhật", example = "1")
//    Long transactionId;

    @FutureOrPresent(message = "Valid until date must be in the present or future")
    @Schema(description = "Thời gian vé có hiệu lực đến, nếu cập nhật", example = "2025-07-25T23:59:59")
    LocalDateTime validUntil;
}