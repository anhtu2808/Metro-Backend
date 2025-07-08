package com.metro.route.dto.response.lineSegment;

import com.metro.route.dto.response.StationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Trả về thông tin chi tiết của một đoạn tuyến đường (Line Segment) trong hệ thống metro")
public class LineSegmentResponse {

    @Schema(description = "ID của đoạn tuyến đường", example = "1")
    Long id;

    @Schema(description = "Thứ tự của đoạn tuyến đường trong tuyến", example = "1")
    Integer order;

    @Schema(description = "Thời gian di chuyển của đoạn tuyến đường (phút)", example = "7")
    Integer duration;

    @Schema(description = "Khoảng cách của đoạn tuyến đường (miles)", example = "0.715")
    Float distance;

    @Schema(description = "ID của ga bắt đầu", example = "2")
    Long startStationId;

    @Schema(description = "ID của ga đến", example = "1")
    Long endStationId;
    StationResponse startStation;
    StationResponse endStation;
}