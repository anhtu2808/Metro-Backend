package com.metro.order.dto.request;

import com.metro.order.enums.TicketStatus;
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
    String sortBy;
    Long userId;
    Boolean isStatic;
    Boolean isStudent;
    String status;
}
