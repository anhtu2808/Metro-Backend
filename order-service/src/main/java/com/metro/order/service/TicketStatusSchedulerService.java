package com.metro.order.service;

import com.metro.order.repository.TicketOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketStatusSchedulerService {
    private final TicketOrderRepository ticketOrderRepository;
    @Scheduled(cron = "0 */5 * * * *") // chạy mỗi 5 phút
    @Transactional
    public void activateTicketsAfter30Days() {
        int updated = ticketOrderRepository.activateTicketsAfter30Days();
        log.info("[Scheduler] Đã chuyển {} vé từ INACTIVE → ACTIVE", updated);
    }

    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void expireTickets() {
        int expired = ticketOrderRepository.expireTickets();
        log.info("[Scheduler] Đã chuyển {} vé từ ACTIVE → EXPIRED", expired);
    }
}
