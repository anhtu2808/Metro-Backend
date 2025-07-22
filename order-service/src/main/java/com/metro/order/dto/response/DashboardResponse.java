package com.metro.order.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse  {
    Long totalOrders;
    Long totalTicketsSold;  // Vé bán thành công
    Long totalUsers;  // Unique users mua vé
    BigDecimal totalRevenue;  // Sum price/amount

    Long staticTicketCount;
    BigDecimal staticTicketRevenue;

    Long dynamicTicketCount;
    BigDecimal dynamicTicketRevenue;

    Long studentTicketCount;
    BigDecimal studentTicketRevenue;

//    Long nonStudentTicketCount;
//    BigDecimal nonStudentTicketRevenue;

    Long completedOrderCount;
    BigDecimal completedOrderRevenue;
    Long cancelledOrderCount;

    LocalDateTime fromDate;
    LocalDateTime toDate;
}
