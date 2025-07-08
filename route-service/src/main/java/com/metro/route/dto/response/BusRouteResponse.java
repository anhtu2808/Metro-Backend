package com.metro.route.dto.response;

import com.metro.route.entity.Station;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Trả về thông tin chi tiết của một tuyến xe buýt liên quan đến một ga trong hệ thống metro")
public class BusRouteResponse {

    @Schema(description = "ID của tuyến xe buýt", example = "1")
    Long id;

    @Schema(description = "Mã tuyến xe buýt, duy nhất", example = "B01")
    String busCode;

    @Schema(description = "Địa điểm bắt đầu của tuyến xe buýt", example = "Bến Thành")
    String startLocation;

    @Schema(description = "Địa điểm kết thúc của tuyến xe buýt", example = "Suối Tiên")
    String endLocation;

    @Schema(description = "Thời gian chờ giữa các chuyến xe buýt (phút)", example = "15")
    int headwayMinutes;

    @Schema(description = "Khoảng cách từ tuyến xe buýt đến ga (km)", example = "2.5")
    Float distanceToStation;

    @Schema(description = "Tên ga xe buýt liên quan", example = "Ga Bến Thành")
    String busStationName;
}