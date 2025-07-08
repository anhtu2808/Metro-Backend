package com.metro.order.dto.response;

import com.metro.order.entity.TicketOrder;
import com.metro.order.repository.httpClient.StationClient;
import com.metro.order.repository.httpClient.TicketTypeClient;
import com.metro.order.repository.httpClient.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketOrderResponseEnricher {
    private final UserClient userClient;
    private final TicketTypeClient ticketTypeClient;
    private final StationClient stationClient;

    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }
    public void enrich(TicketOrder entity, TicketOrderResponse response) {
        try {
            if (isAdmin()) {
                response.setUser(userClient.getUser(entity.getUserId()).getResult());
            } else {
                response.setUser(userClient.getMyInfo().getResult());
            }
            if (entity.getTicketTypeId() != null)
                response.setTicketType(ticketTypeClient.getTicketTypesById(entity.getTicketTypeId()).getResult());
            if (entity.getStartStationId() != null)
                response.setStartStation(stationClient.getStationById(entity.getStartStationId()).getResult());
            if (entity.getEndStationId() != null)
                response.setEndStation(stationClient.getStationById(entity.getEndStationId()).getResult());
        } catch (Exception e) {
            log.warn("Failed to enrich TicketOrderResponse: {}", e.getMessage());
        }
    }
}
