package com.metro.ticket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Trả về thông tin chi tiết của một loại vé (Ticket Type) trong hệ thống metro")
public class TicketTypeResponse {

    @Schema(description = "ID của loại vé", example = "1")
    Long id;

    @Schema(description = "Tên của loại vé", example = "Vé lượt, Vé tháng, Vé tuần")
    String name;

    @Schema(description = "Mô tả chi tiết về loại vé", example = "Vé sử dụng cho một chuyến đi trong ngày")
    String description;

    @Schema(description = "Số ngày hiệu lực của vé", example = "30")
    Integer validityDays;

    @JsonProperty("isStudent")
    @Schema(description = "Loại vé có dành cho học sinh/sinh viên hay không", example = "false")
    Boolean isStudent;

    @Schema(description = "Loại vé có giá cố định hay không", example = "true")
    boolean isStatic; // Đảm bảo là boolean

    @Schema(description = "Giá của loại vé", example = "7000.00")
    BigDecimal price;
}