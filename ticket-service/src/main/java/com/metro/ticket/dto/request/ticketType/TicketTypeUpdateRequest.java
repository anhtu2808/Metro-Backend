package com.metro.ticket.dto.request.ticketType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Yêu cầu cập nhật thông tin một loại vé (Ticket Type) trong hệ thống metro")
public class TicketTypeUpdateRequest {

    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Schema(description = "Tên mới của loại vé, phải duy nhất nếu được cung cấp", example = "Vé tháng")
    String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Mô tả mới của loại vé", example = "Vé sử dụng không giới hạn trong 30 ngày")
    String description;

    @Positive(message = "Validity days must be positive")
    @Schema(description = "Số ngày hiệu lực mới của vé, nếu có", example = "30")
    Integer validityDays;

    @Schema(description = "Loại vé có dành cho học sinh/sinh viên hay không", example = "true")
    Boolean isStudent;

    @Schema(description = "Loại vé có giá cố định hay không", example = "true")
    boolean isStatic;

    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @Schema(description = "Giá mới của loại vé, phải là số dương nếu được cung cấp", example = "150000.00")
    BigDecimal price;
}
