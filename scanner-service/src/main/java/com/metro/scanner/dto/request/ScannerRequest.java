package com.metro.scanner.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScannerRequest {
    Long lineId;
    Long stationId;
    String ticketOrderToken;
    Boolean isCheckIn; // true: check-in, false: check-out
}
