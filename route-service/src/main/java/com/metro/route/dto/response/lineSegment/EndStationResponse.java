package com.metro.route.dto.response.lineSegment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Trả về thông tin các ga đến của một tuyến đường")
public class EndStationResponse {

    @Schema(description = "ID của tuyến đường (metro line)", example = "1")
    Long lineId;

    @Schema(description = "ID của ga đến", example = "13")
    Long id;

    @Schema(description = "Tên của ga đến", example = "Suối Tiên")
    String name;

    @Schema(description = "Mã của ga đến", example = "GA13")
    String stationCode;

    @Schema(description = "URL hình ảnh của ga đến", example = "images/suoi-tien.jpg")
    String imageUrl;

    @Schema(description = "Địa chỉ của ga đến", example = "TP. Thủ Đức, TP.HCM")
    String address;

    @Schema(description = "Vĩ độ của ga đến", example = "10.8602")
    String latitude;

    @Schema(description = "Kinh độ của ga đến", example = "106.8034")
    String longitude;

    @Schema(description = "Thứ tự của ga đến trong tuyến đường", example = "12")
    Integer order;

    @Schema(description = "Giá vé từ ga bắt đầu đến ga này (làm tròn lên số nguyên)", example = "10")
    BigDecimal fare;
}