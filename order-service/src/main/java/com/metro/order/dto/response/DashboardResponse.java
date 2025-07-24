package com.metro.order.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.metro.order.dto.response.TicketTypeStatisticResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse  {
    Long totalOrders;
    Long totalUsers;  // Unique users mua vé
    BigDecimal totalRevenue;  // Sum price/amount

    Long staticTicketCount;
    BigDecimal staticTicketRevenue;

    Long dynamicTicketCount;
    BigDecimal dynamicTicketRevenue;

    Long studentTicketCount;
    BigDecimal studentTicketRevenue;

    List<TicketTypeStatisticResponse> ticketTypeStats;


    Long completedOrderCount;
    BigDecimal completedOrderRevenue;
    Long cancelledOrderCount;

    LocalDateTime fromDate;
    LocalDateTime toDate;
}
