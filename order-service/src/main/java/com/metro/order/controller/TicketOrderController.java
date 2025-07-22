package com.metro.order.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderFilterRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.DashboardResponse;
import com.metro.order.dto.response.FareAdjustmentReponse;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.enums.TicketStatus;
import com.metro.order.exception.AppException;
import com.metro.order.exception.ErrorCode;
import com.metro.order.repository.httpClient.UserClient;
import com.metro.order.saga.service.FareAdjustmentOrchestrator;
import com.metro.order.service.TicketOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ticket-orders")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(
        name = "Ticket Orders",
        description = "API để quản lý các đơn hàng vé (Ticket Orders) trong hệ thống metro"
)


public class TicketOrderController{

    @Value("${internal.secret}")
    String internalSecret;
    final UserClient userClient;
    final TicketOrderService ticketOrderService;
    final FareAdjustmentOrchestrator fareAdjustmentOrchestrator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TicketOrderResponse> createTicketOrder(@RequestBody TicketOrderCreationRequest request) {
        TicketOrderResponse response = ticketOrderService.createTicketOrder(request);
        return ApiResponse.<TicketOrderResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Ticket order created successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TicketOrderResponse> getTicketOrderById(@PathVariable Long id) {
        TicketOrderResponse response = ticketOrderService.getTicketOrderById(id);
        return ApiResponse.<TicketOrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Ticket order retrieved successfully")
                .result(response)
                .build();
    }


    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateTicketOrderStatus(@PathVariable Long id,
                                                        @RequestParam TicketStatus status,
                                                        @RequestHeader(value = "X-INTERNAL-SECRET", required = false) String providedSecret) {
        log.info("💡 Provided secret: {}", providedSecret);
        log.info("🔐 Expected secret: {}", internalSecret);
        if (providedSecret == null || !providedSecret.equals(internalSecret)) {
            return ApiResponse.<Void>builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Unauthorized: Invalid internal secret")
                    .build();
        }

        ticketOrderService.updateTicketOrderStatus(id, status);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Ticket order status updated successfully")
                .build();
    }

    @GetMapping("/by-user/{userId}") //all order cua user find theo userId cho permission ticket_order:viewall
    public ApiResponse<PageResponse<TicketOrderResponse>> getOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<TicketOrderResponse> response = ticketOrderService
                .getOrdersByUserId(userId, page, size);
        return ApiResponse.<PageResponse<TicketOrderResponse>>builder()
                .result(response)
                .code(200)
                .message("Success")
                .build();
    }

    @GetMapping("/by-user") //all order cua user dang dang nhap
    public ApiResponse<PageResponse<TicketOrderResponse>> getMyOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var myInfo = userClient.getMyInfo().getResult();
        var response = ticketOrderService.getOrdersByUserId(myInfo.getId(), page, size);

        return ApiResponse.<PageResponse<TicketOrderResponse>>builder()
                .result(response)
                .code(200)
                .message("Fetched ticket orders for current user")
                .build();
    }

    @GetMapping("")
    public ApiResponse<PageResponse<TicketOrderResponse>> findAllWithFilter(@ModelAttribute TicketOrderFilterRequest filterRequest) {
        return ApiResponse.<PageResponse<TicketOrderResponse>>builder()
                .result(ticketOrderService.getAllTicketOrders(filterRequest))
                .message("Ticket orders retrieved successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<PageResponse<TicketOrderResponse>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        TicketOrderFilterRequest filterRequest = new TicketOrderFilterRequest();
        filterRequest.setPage(page);
        filterRequest.setSize(size);
        filterRequest.setSortBy(sort);

        return ApiResponse.<PageResponse<TicketOrderResponse>>builder()
                .result(ticketOrderService.getAllTicketOrders(filterRequest))
                .message("Ticket orders retrieved successfully")
                .code(HttpStatus.OK.value())
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<TicketOrderResponse> updateTicketOrder(@PathVariable Long id, @RequestBody TicketOrderUpdateRequest request) {
        TicketOrderResponse response = ticketOrderService.updateTicketOrder(id, request);
        return ApiResponse.<TicketOrderResponse>builder().result(response).build();
    }
    @PutMapping("/{id}/status-purchase")
    public ResponseEntity<Void> updateTicketOrderStatus(@PathVariable Long id,
                                                        @RequestParam TicketStatus status,
                                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime purchaseDate,
                                                        @RequestHeader(value = "X-INTERNAL-SECRET", required = false) String providedSecret) {
        log.info("💡 Provided secret: {}", providedSecret);
        log.info("🔐 Expected secret: {}", internalSecret);
        if (providedSecret == null || !providedSecret.equals(internalSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ticketOrderService.updateTicketOrderStatusAndPurchase(id, status, purchaseDate);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{ticketOrderId}/adjust-fare")
    public ApiResponse<FareAdjustmentReponse> adjustFare(
            @PathVariable Long ticketOrderId,
            @RequestParam Long newEndStationId,
            HttpServletRequest request) {
        FareAdjustmentReponse response = fareAdjustmentOrchestrator.execute(ticketOrderId, newEndStationId, request);
        return ApiResponse.<FareAdjustmentReponse>builder()
                .code(HttpStatus.OK.value())
                .message("Yêu cầu điều chỉnh giá vé đã được xử lý")
                .result(response)
                .build();
    }
    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponse> getDashboardData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

        if (toDate.isBefore(fromDate)) {
            throw new AppException(ErrorCode.INVALID_DATE_RANGE, "To date must be after From date");
        }
        DashboardResponse response = ticketOrderService.getDashboardTicketOrder(fromDate, toDate);

        return ApiResponse.<DashboardResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Fetched dashboard data successfully")
                .result(response)
                .build();
    }

}
