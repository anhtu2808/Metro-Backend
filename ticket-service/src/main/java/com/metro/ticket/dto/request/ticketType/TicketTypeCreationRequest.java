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
@Schema(description = "Yêu cầu tạo mới một loại vé (Ticket Type) cho hệ thống metro")
public class TicketTypeCreationRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Schema(description = "Tên của loại vé, phải duy nhất", example = "Vé lượt")
    String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Mô tả chi tiết về loại vé", example = "Vé sử dụng cho một chuyến đi trong ngày")
    String description;

    @Positive(message = "Validity days must be positive")
    @Max(value = 365, message = "Validity days must not exceed 365 days")
    @Schema(description = "Số ngày hiệu lực của vé, nếu có", example = "30")
    Integer validityDays;

    @Schema(description = "Loại vé có dành cho học sinh/sinh viên hay không", example = "false")
    Boolean isStudent = false;

    @Schema(description = "Loại vé có giá cố định hay không", example = "true")
    boolean isStatic = true;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @Schema(description = "Giá của loại vé, phải là số dương", example = "7000.00")
    BigDecimal price;
}
