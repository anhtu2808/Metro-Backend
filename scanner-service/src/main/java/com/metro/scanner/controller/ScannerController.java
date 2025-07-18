package com.metro.scanner.controller;


import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.scanner.dto.request.ScannerRequest;
import com.metro.scanner.dto.response.ScannerResponse;
import com.metro.scanner.dto.response.TicketOrderResponse;
import com.metro.scanner.service.ScannerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/validate")
    public ApiResponse<ScannerResponse> validateTicket(@RequestBody ScannerRequest request) {
        return ApiResponse.<ScannerResponse>builder()
                .result(scannerService.validateTicket(request))
                .build();
    }

}
