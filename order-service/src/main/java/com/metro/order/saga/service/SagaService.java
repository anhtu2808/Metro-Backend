package com.metro.order.saga.service;

import com.metro.order.enums.SagaStatus;
import com.metro.order.repository.SagaStateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SagaService {
    SagaStateRepository sagaStateRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void cleanStaleSagas() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(1);
        int affected = sagaStateRepository.markStaleSagasAsFailed(
                SagaStatus.PENDING,
                SagaStatus.FAILED,
                cutoff
        );
    if (affected > 0) {
        log.warn("✅ Cleaned {} stale saga(s) older than {}", affected, cutoff);
    }
    }
}
