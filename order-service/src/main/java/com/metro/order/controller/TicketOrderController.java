package com.metro.order.controller;
import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.service.AbstractService;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.entity.TicketOrder;
import com.metro.order.service.TicketOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket-orders")
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

    public TicketOrderController(final TicketOrderService service) {
        super(service);
    }
}