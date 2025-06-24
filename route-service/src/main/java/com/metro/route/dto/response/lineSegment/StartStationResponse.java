package com.metro.route.dto.response.lineSegment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Trả về thông tin các ga bắt đầu của một tuyến đường")
public class StartStationResponse {


    @Schema(description = "ID của ga bắt đầu", example = "2")
    Long id;

    @Schema(description = "Tên của ga bắt đầu", example = "Nhà hát TP")
    String name;

    @Schema(description = "Mã của ga bắt đầu", example = "GA02")
    String stationCode;

    @Schema(description = "URL hình ảnh của ga bắt đầu", example = "images/nha-hat-tp.jpg")
    String imageUrl;

    @Schema(description = "Địa chỉ của ga bắt đầu", example = "Quận 1, TP.HCM")
    String address;

    @Schema(description = "Vĩ độ của ga bắt đầu", example = "10.7769")
    String latitude;

    @Schema(description = "Kinh độ của ga bắt đầu", example = "106.7023")
    String longitude;

    @Schema(description = "Thứ tự của ga bắt đầu trong tuyến đường", example = "1")
    Integer order;
}
