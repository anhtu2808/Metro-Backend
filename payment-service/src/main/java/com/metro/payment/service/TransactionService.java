package com.metro.payment.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.payment.dto.response.TransactionResponse;

public interface TransactionService {
    PageResponse<TransactionResponse> getTransactionsByUserId(Long userId, int page, int size);
    Long getCurrentUserId();
}
