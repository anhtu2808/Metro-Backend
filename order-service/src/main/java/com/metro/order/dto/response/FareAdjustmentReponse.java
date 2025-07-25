package com.metro.order.dto.response;

import com.metro.order.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FareAdjustmentReponse {
    VNPayResponse vnPayResponse;
    @Schema(description = "ID của đơn hàng vé", example = "1")
    Long id;

    @Schema(description = "Mã vé duy nhất", example = "TCKT-20250625-001")
    String ticketCode;

    @Schema(description = "Thông tin người dùng đặt vé")
    UserResponse user;

    @Schema(description = "ID của giao dịch thanh toán liên quan", example = "1")
    Long transactionId;

    @Schema(description = "Trạng thái của vé", example = "ACTIVE")
    TicketStatus status;

    @Schema(description = "Thông tin loại vé")
    TicketTypeResponse ticketType;

    @Schema(description = "Thông tin ga bắt đầu")
    StationResponse startStation;

    @Schema(description = "Thông tin ga kết thúc")
    StationResponse endStation;

    @Schema(description = "Giá của vé", example = "7000.00")
    BigDecimal price;

    @Schema(description = "Ngày giờ mua vé", example = "2025-06-25T09:00:00")
    LocalDateTime purchaseDate;

    @Schema(description = "Thời gian vé có hiệu lực đến", example = "2025-07-25T23:59:59")
    LocalDateTime validUntil;
    Long sagaId;

}
