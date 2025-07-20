package com.metro.payment.controller;


import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.payment.dto.response.TransactionResponse;
import com.metro.payment.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/user/{userId}")
    public ApiResponse<PageResponse<TransactionResponse>> getTransactionsByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<TransactionResponse> response = transactionService.getTransactionsByUserId(userId, page, size);
        return ApiResponse.<PageResponse<TransactionResponse>>builder().result(response)
                .code(200)
                .message("Success")
                .build();
    }

    @GetMapping("/my-transactions")
    public ApiResponse<PageResponse<TransactionResponse>> getMyTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long currentUserId = transactionService.getCurrentUserId();
        PageResponse<TransactionResponse> response = transactionService.getTransactionsByUserId(currentUserId, page, size);
        return ApiResponse.<PageResponse<TransactionResponse>>builder().result(response)
                .code(200)
                .message("Success")
                .build();
    }

}
