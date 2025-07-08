package com.metro.order.controller;
import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.entity.TicketOrder;
import com.metro.order.enums.TicketStatus;
import com.metro.order.repository.httpClient.UserClient;
import com.metro.order.service.TicketOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket-orders")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(
        name = "Ticket Orders",
        description = "API để quản lý các đơn hàng vé (Ticket Orders) trong hệ thống metro"
)


public class TicketOrderController extends AbstractController<
        TicketOrder,
        TicketOrderCreationRequest,
        TicketOrderUpdateRequest,
        TicketOrderResponse> {

    String internalSecret;
    UserClient userClient;
    public TicketOrderController(final TicketOrderService service, @Value("${internal.secret}") String internalSecret
    , UserClient userClient) {
        super(service);
        this.internalSecret = internalSecret;
        this.userClient = userClient;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateTicketOrderStatus(@PathVariable Long id,
                                                        @RequestParam TicketStatus status,
                                                        @RequestHeader(value = "X-INTERNAL-SECRET", required = false) String providedSecret) {
        log.info("💡 Provided secret: {}", providedSecret);
        log.info("🔐 Expected secret: {}", internalSecret);
        if (providedSecret == null || !providedSecret.equals(internalSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ((TicketOrderService) service).updateTicketOrderStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-user/{userId}") //all order cua user find theo userId cho permission ticket_order:viewall
    public ApiResponse<PageResponse<TicketOrderResponse>> getOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<TicketOrderResponse> response = ((TicketOrderService) service)
                .findOrdersByUserId(userId, page, size);
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
        var response = ((TicketOrderService) service).findOrdersByUserId(myInfo.getId(), page, size);

        return ApiResponse.<PageResponse<TicketOrderResponse>>builder()
                .result(response)
                .code(200)
                .message("Fetched ticket orders for current user")
                .build();
    }


}