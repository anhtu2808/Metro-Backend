package com.metro.order.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketOrderFilterRequest {
    int page;
    int size;
    Long userId;
    Boolean isStatic;
    Boolean isStudent;
}
