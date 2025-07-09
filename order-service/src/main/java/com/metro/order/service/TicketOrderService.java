package com.metro.order.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.common_lib.service.AbstractService;
import com.metro.order.TicketOrderMapper;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderFilterRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.dto.response.TicketOrderResponseEnricher;
import com.metro.order.dto.response.TicketTypeResponse;
import com.metro.order.dto.response.UserResponse;
import com.metro.order.entity.TicketOrder;
import com.metro.order.enums.TicketStatus;
import com.metro.order.exception.AppException;
import com.metro.order.exception.ErrorCode;
import com.metro.order.repository.TicketOrderRepository;
import com.metro.order.repository.httpClient.*;
import com.metro.order.specification.TicketOrderSpecification;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketOrderService extends AbstractService<
        TicketOrder,
        TicketOrderCreationRequest,
        TicketOrderUpdateRequest,
        TicketOrderResponse> {
    UserClient userClient;
    TicketTypeClient ticketTypeClient;
    StationClient stationClient;
    DynamicPriceClient dynamicPriceClient;
    LineSegmentClient lineSegmentClient;
    TicketOrderRepository ticketOrderRepository;
    TicketOrderResponseEnricher responseEnricher;


    private final String SIGNER_KEY;

    protected TicketOrderService(TicketOrderRepository ticketOrderRepository, TicketOrderMapper entityMapper,
                                 UserClient userClient, TicketTypeClient ticketTypeClient,
                                 StationClient stationClient,
                                 DynamicPriceClient dynamicPriceClient, LineSegmentClient lineSegmentClient,
                                 TicketOrderResponseEnricher responseEnricher, @Value("${spring.jwt.signerKey}") String signerSecret) {
        super(ticketOrderRepository, entityMapper);
        this.userClient = userClient;
        this.ticketTypeClient = ticketTypeClient;
        this.stationClient = stationClient;
        this.dynamicPriceClient = dynamicPriceClient;
        this.lineSegmentClient = lineSegmentClient;
        this.ticketOrderRepository = ticketOrderRepository;
        this.responseEnricher = responseEnricher;
        this.SIGNER_KEY = signerSecret;
    }

    @Override
    protected void beforeCreate(TicketOrder entity) {
        if (entity.getUserId() == null) {
            try {
                var userInfo = userClient.getMyInfo().getResult();
                if (userInfo == null || userInfo.getId() == null) {
                    throw new AppException(ErrorCode.UNAUTHENTICATED);
                }
                entity.setUserId(userInfo.getId());
            } catch (Exception e) {
                log.error("Failed to get authenticated user info", e);
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
        }
        var ticketType = ticketTypeClient.getTicketTypesById(entity.getTicketTypeId()).getResult();
        if (ticketType == null) {
            throw new AppException(ErrorCode.TICKET_TYPE_NOT_FOUND);
        }

        boolean isUnlimited = ticketType.isStatic() || ticketType.getIsStudent();

        if (!isUnlimited) {
            try {
                stationClient.getStationById(entity.getStartStationId()).getResult();
            } catch (Exception e) {
                throw new AppException(ErrorCode.START_STATION_NOT_FOUND);
            }

            try {
                stationClient.getStationById(entity.getEndStationId()).getResult();
            } catch (Exception e) {
                throw new AppException(ErrorCode.END_STATION_NOT_FOUND);
            }

            if (entity.getStartStationId().equals(entity.getEndStationId())) {
                throw new AppException(ErrorCode.INVALID_STATION_COMBINATION);
            }
        } else {
            entity.setStartStationId(null);
            entity.setEndStationId(null);
        }

        if (isUnlimited) {
            entity.setPrice(ticketType.getPrice());
        } else {
            Long lineId = lineSegmentClient
                    .getLineIdByStartAndEnd(entity.getStartStationId(), entity.getEndStationId())
                    .getResult();

            var dynamicPrice = dynamicPriceClient
                    .getPriceByStartAndEnd(lineId, entity.getStartStationId(), entity.getEndStationId())
                    .getResult();

            entity.setPrice(dynamicPrice.getPrice());
        }

        entity.setStatus(entity.getStatus() != null ? entity.getStatus() : TicketStatus.UNPAID);
        entity.setPurchaseDate(LocalDateTime.now());
        if (ticketType.getValidityDays() != null) {
            entity.setValidUntil(entity.getPurchaseDate().plusDays(ticketType.getValidityDays()));
        }
        LocalDateTime purchaseDate = entity.getPurchaseDate();

        long dailyCount = ticketOrderRepository.countByPurchaseDateBetween(
                purchaseDate.toLocalDate().atStartOfDay(),
                purchaseDate.toLocalDate().plusDays(1).atStartOfDay()
        );

        String datePart = purchaseDate.toLocalDate().toString().replace("-", "");
        String sequence = String.format("%03d", dailyCount + 1); // 001, 002...
        String ticketCode = String.format("TCKT-%s-%s", datePart, sequence);
        entity.setTicketCode(ticketCode);
    }


    @Override
    protected void beforeUpdate(TicketOrder oldEntity, TicketOrder newEntity) {

    }

    @Override
    public TicketOrderResponse create(TicketOrderCreationRequest request) {
        TicketOrder entity = entityMapper.toEntity(request);
        beforeCreate(entity);
        TicketOrder saved = repository.save(entity);

        TicketOrderResponse response = entityMapper.toResponse(saved);
        responseEnricher.enrich(saved, response);
        return response;
    }

    @Override
    public TicketOrderResponse findById(Long id) {
        TicketOrder entity = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_ORDER_NOT_FOUND));
        String ticketToken = generateTicketToken(entity.getId());
        TicketOrderResponse response = entityMapper.toResponse(entity);
        responseEnricher.enrich(entity, response);
        response.setTicketQRToken(ticketToken); // Add QR token to response
        return response;
    }

    public void updateTicketOrderStatus(Long ticketOrderId, TicketStatus status) {
        var ticketOrder = ticketOrderRepository.findById(ticketOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_ORDER_NOT_FOUND));

        ticketOrder.setStatus(status);
        ticketOrderRepository.save(ticketOrder);
    }

    public PageResponse<TicketOrderResponse> findOrdersByUserId(Long userId, int page, int size) {
        Long actualUserId;
        if (page <= 0) page = 1;
        if (size <= 0) size = 10;
        if (hasPermission("ticket_order:viewall")) {
            actualUserId = userId;
        } else {
            if (!isCurrentUser(userId)) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
            actualUserId = userId;
        }

        var sort = Sort.by(Sort.Direction.DESC, "id");
        var pageable = PageRequest.of(page - 1, size, sort);
        var pageResult = ticketOrderRepository.findAllByUserId(actualUserId, pageable);

        var responses = pageResult.getContent().stream().map(entity -> {
            TicketOrderResponse response = entityMapper.toResponse(entity);
            responseEnricher.enrich(entity, response);
            return response;
        }).toList();

        return PageResponse.<TicketOrderResponse>builder()
                .currentPage(page)
                .totalPages(pageResult.getTotalPages())
                .totalElements(pageResult.getTotalElements())
                .pageSize(size)
                .data(responses)
                .build();
    }

    private boolean isCurrentUser(Long userId) {
        try {
            var myInfo = userClient.getMyInfo().getResult();
            return myInfo != null && Objects.equals(myInfo.getId(), userId);
        } catch (Exception e) {
            log.error("Lỗi xác thực người dùng", e);
            return false;
        }
    }

    private boolean hasPermission(String permission) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(permission));
    }

    public PageResponse<TicketOrderResponse> getAllTicketOrders(TicketOrderFilterRequest req) {
        List<Long> staticTypeIds = null;
        if (req.getIsStatic() != null) {
            staticTypeIds = ticketTypeClient.getAllTicketTypes(1, 1000).getResult().getData().stream()
                    .filter(t -> t.isStatic() == req.getIsStatic())
                    .map(TicketTypeResponse::getId)
                    .toList();
            if (staticTypeIds.isEmpty()) {
                return PageResponse.<TicketOrderResponse>builder()
                        .data(List.of())
                        .totalElements(0)
                        .totalPages(1)
                        .pageSize(1)
                        .data(List.of())
                        .build();
            }
        }
        var spec = TicketOrderSpecification.withFilter(req, staticTypeIds);
        Pageable pageable = PageRequest.of(
                req.getPage() - 1,
                req.getSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        Page<TicketOrder> orders = ticketOrderRepository.findAll(spec, pageable);
        return PageResponse.<TicketOrderResponse>builder()
                .data(orders.getContent().stream().map(entityMapper::toResponse).toList())
                .totalElements(orders.getTotalElements())
                .totalPages(orders.getTotalPages())
                .pageSize(pageable.getPageSize())
                .currentPage(pageable.getPageNumber())
                .build();
    }

    /**
     * Sinh JWT chứa ticketOrderId để nhúng vào QR
     */
    private String generateTicketToken(Long ticketOrderId) {

        // 1. Lấy đơn vé
        TicketOrder order = ticketOrderRepository.findById(ticketOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_ORDER_NOT_FOUND));

        // 2. Lấy secret
        if (SIGNER_KEY == null || SIGNER_KEY.isBlank()) {
            throw new AppException(ErrorCode.SIGNER_KEY_NOT_FOUND);
        }

        // 3. Tạo JWT claims
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(60L);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("ticket")                         // tuỳ ý
                .claim("ticketOrderId", order.getId())
                .claim("ticketCode", order.getTicketCode())// thêm gì cũng được
                .issueTime(Date.from(now))
                .expirationTime(Date.from(exp))
                .build();

        try {
            // 4. Ký HMAC-SHA256
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(new MACSigner(SIGNER_KEY.getBytes(StandardCharsets.UTF_8)));

            // 5. Trả chuỗi token
            return signedJWT.serialize();

        } catch (JOSEException e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
