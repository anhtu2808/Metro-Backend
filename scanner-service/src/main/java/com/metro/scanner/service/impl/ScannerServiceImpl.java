package com.metro.scanner.service.impl;

import com.metro.scanner.client.OrderServiceClient;
import com.metro.scanner.dto.request.ScannerRequest;
import com.metro.scanner.dto.response.ScannerResponse;
import com.metro.scanner.dto.response.TicketOrderResponse;
import com.metro.scanner.enums.TicketStatus;
import com.metro.scanner.exception.AppException;
import com.metro.scanner.exception.ErrorCode;
import com.metro.scanner.service.ScannerService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScannerServiceImpl implements ScannerService {

    @Value("${spring.jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${internal.secret}")
    private String INTERNAL_SECRET;

    private final OrderServiceClient orderServiceClient;

    @Override
    public ScannerResponse validateTicket(ScannerRequest request) {
        TicketOrderResponse ticketOrder = getTicketOrderByToken(request.getTicketOrderToken());
        if (ticketOrder.getStatus().equals(TicketStatus.UNPAID)) {
            throw new AppException(ErrorCode.TICKET_ORDER_UNPAID);
        }
        if (ticketOrder.getStatus().equals(TicketStatus.EXPIRED)) {
            throw new AppException(ErrorCode.TICKET_ORDER_EXPIRED);
        }
        if (ticketOrder.getStatus().equals(TicketStatus.INACTIVE)) {
            throw new AppException(ErrorCode.TICKET_ORDER_INACTIVE);
        }
        if (request.getIsCheckIn().equals(Boolean.TRUE)) {
            validateCheckIn(request, ticketOrder);
        } else {
            validateCheckOut(request, ticketOrder);
        }
        return ScannerResponse.builder()
                .isValidate(true)
                .build();
    }

    @Override
    public TicketOrderResponse getTicketOrderByToken(String token) {
        Long ticketOrderId = getTicketOrderIdFromToken(token);
        return orderServiceClient.getTicketOrderById(ticketOrderId).getResult();
    }

    private Long getTicketOrderIdFromToken(String token) {
        try {
            // 1. Lấy secret từ biến môi trường (hoặc system property)
            String secret = SIGNER_KEY;
            if (secret == null || secret.isBlank()) {
                throw new AppException(ErrorCode.SIGNER_KEY_NOT_FOUND);
            }

            // 2. Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // 3. Xác thực chữ ký HMAC-SHA256
            if (!signedJWT.verify(new MACVerifier(secret.getBytes(StandardCharsets.UTF_8)))) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }

            // 4. Lấy claims
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // 5. Kiểm tra hết hạn
            if (claims.getExpirationTime() == null ||
                    claims.getExpirationTime().toInstant().isBefore(Instant.now())) {
                throw new AppException(ErrorCode.EXPIRED_TOKEN);
            }

            // 6. Đọc ticketOrderId
            Long ticketOrderId = claims.getLongClaim("ticketOrderId");
            if (ticketOrderId == null) {
                throw new AppException(ErrorCode.TICKET_ORDER_ID_NOT_FOUND);
            }

            return ticketOrderId;

        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    private void validateCheckIn(ScannerRequest request, TicketOrderResponse ticketOrder) {
        if (!ticketOrder.getStatus().equals(TicketStatus.ACTIVE)) {
            throw new AppException(ErrorCode.TICKET_STATUS_INVALID);
        }
        if (ticketOrder.getTicketType().isStatic()) {
            orderServiceClient.updateTicketOrderStatus(ticketOrder.getId(), TicketStatus.USING, INTERNAL_SECRET);
        } else {
            if (request.getStationId().equals(ticketOrder.getStartStation().getId())) {
                orderServiceClient.updateTicketOrderStatus(ticketOrder.getId(), TicketStatus.USING, INTERNAL_SECRET);
            } else {
                throw new AppException(ErrorCode.INVALID_CHECKIN_STATION);
            }
        }
    }

    private void validateCheckOut(ScannerRequest request, TicketOrderResponse ticketOrder) {
        if (!ticketOrder.getStatus().equals(TicketStatus.USING)) {
            throw new AppException(ErrorCode.TICKET_STATUS_INVALID);
        }
        if (ticketOrder.getTicketType().isStatic()) {
            LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
            if (ticketOrder.getValidUntil().isBefore(now)) {
                orderServiceClient.updateTicketOrderStatus(ticketOrder.getId(), TicketStatus.EXPIRED, INTERNAL_SECRET);
            } else {
                orderServiceClient.updateTicketOrderStatus(ticketOrder.getId(), TicketStatus.ACTIVE, INTERNAL_SECRET);
            }
        } else {
            if (request.getStationId().equals(ticketOrder.getEndStation().getId())) {
                orderServiceClient.updateTicketOrderStatus(ticketOrder.getId(), TicketStatus.EXPIRED, INTERNAL_SECRET);
            } else {
                throw new AppException(ErrorCode.INVALID_CHECKOUT_STATION);
            }
        }
    }


}
