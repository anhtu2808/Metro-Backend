package com.metro.order.dto.request;

import com.metro.order.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Yêu cầu tạo mới một đơn hàng vé (Ticket Order) trong hệ thống metro")
public class TicketOrderCreationRequest {

//    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    @Schema(description = "ID của người dùng đặt vé", example = "1")
    Long userId;

    @NotNull(message = "Ticket Type ID is required")
    @Positive(message = "Ticket Type ID must be positive")
    @Schema(description = "ID của loại vé được chọn", example = "1")
    Long ticketTypeId;

//    @Positive(message = "Transaction ID must be positive")
//    @Schema(description = "ID của giao dịch thanh toán, nếu có", example = "1")
//    Long transactionId;

//    @NotNull(message = "Start Station ID is required")
    @Positive(message = "Start Station ID must be positive")
    @Schema(description = "ID của ga bắt đầu", example = "1")
    Long startStationId;

//    @NotNull(message = "End Station ID is required")
    @Positive(message = "End Station ID must be positive")
    @Schema(description = "ID của ga kết thúc", example = "2")
    Long endStationId;

//    @NotNull(message = "Status is required")
    @Schema(description = "Trạng thái của vé", example = "UNPAID")
    TicketStatus status;

    @FutureOrPresent(message = "Valid until date must be in the present or future")
    @Schema(description = "Thời gian vé có hiệu lực đến", example = "2025-07-25T23:59:59")
    LocalDateTime validUntil;

    // Custom validation to ensure start and end stations are different
    @AssertTrue(message = "Start station and end station must be different")
    public boolean isValidStationIds() {
        if (startStationId == null && endStationId == null) {
            return true;
        }
        return startStationId != null && endStationId != null && !startStationId.equals(endStationId);
    }
}