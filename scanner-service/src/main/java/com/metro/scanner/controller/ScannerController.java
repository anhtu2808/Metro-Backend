package com.metro.scanner.controller;


import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.scanner.dto.response.TicketOrderResponse;
import com.metro.scanner.service.ScannerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scanner")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScannerController {
    ScannerService scannerService;

    @GetMapping("/ticket-orders/by-token/{token}")
    public ApiResponse<TicketOrderResponse> getTicketOrderByToken(@PathVariable String token) {
        return ApiResponse.<TicketOrderResponse>builder()
                .result(scannerService.getTicketOrderByToken(token))
                .build();
    }
}
