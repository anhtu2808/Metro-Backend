package com.metro.route.dto.request.busRoute;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Yêu cầu cập nhật thông tin một tuyến xe buýt trong hệ thống metro")
public class BusRouteUpdateRequest {

    @Size(min = 2, max = 10, message = "Bus code must be between 2 and 10 characters")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Bus code must contain only uppercase letters, numbers, or hyphens")
    @Schema(description = "Mã tuyến xe buýt mới, phải duy nhất nếu được cung cấp", example = "B01")
    String busCode;

    @Positive(message = "Headway minutes must be positive")
    @Min(value = 5, message = "Headway minutes must be at least 5 minutes")
    @Max(value = 120, message = "Headway minutes must not exceed 120 minutes")
    @Schema(description = "Thời gian chờ mới giữa các chuyến xe buýt (phút)", example = "15")
    Integer headwayMinutes;

    @Positive(message = "Distance to station must be positive")
    @DecimalMin(value = "0.1", message = "Distance to station must be at least 0.1 km")
    @DecimalMax(value = "100.0", message = "Distance to station must not exceed 100 km")
    @Schema(description = "Khoảng cách mới từ tuyến xe buýt đến ga (km)", example = "2.5")
    Float distanceToStation;

    @Size(min = 3, max = 100, message = "Bus station name must be between 3 and 100 characters")
    @Schema(description = "Tên ga xe buýt mới liên quan", example = "Ga Bến Thành")
    String busStationName;

    @Positive(message = "Station ID must be a positive number")
    @Schema(description = "ID mới của ga liên quan đến tuyến xe buýt", example = "1")
    Long stationId;

    @Size(min = 3, max = 100, message = "Start location must be between 3 and 100 characters")
    @Schema(description = "Địa điểm bắt đầu mới của tuyến xe buýt", example = "Bến Thành")
    String startLocation;

    @Size(min = 3, max = 100, message = "End location must be between 3 and 100 characters")
    @Schema(description = "Địa điểm kết thúc mới của tuyến xe buýt", example = "Suối Tiên")
    String endLocation;
}