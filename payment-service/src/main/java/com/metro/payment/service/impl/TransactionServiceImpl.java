package com.metro.payment.service.impl;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.payment.dto.response.TransactionResponse;
import com.metro.payment.dto.response.UserResponse;
import com.metro.payment.entity.Transaction;
import com.metro.payment.mapper.TransactionMapper;
import com.metro.payment.repository.TransactionRepository;
import com.metro.payment.repository.httpClient.UserClient;
import com.metro.payment.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;
    UserClient userClient;


    @Override
    public PageResponse<TransactionResponse> getTransactionsByUserId(Long userId, int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean canViewAll = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("TRANSACTION_VIEW_ALL"));
        boolean canViewSelf = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("TRANSACTION_VIEW_SELF"));

        if (!canViewAll) {
            if (!canViewSelf) {
                throw new AccessDeniedException("Bạn không có quyền truy cập giao dịch.");
            }

            Long currentUserId = userClient.getMyInfo().getResult().getId();
            if (!currentUserId.equals(userId)) {
                throw new AccessDeniedException("Bạn chỉ được xem giao dịch của chính mình.");
            }
        }
        Page<Transaction> pages = transactionRepository.findByUserId(userId, PageRequest.of(page, size));
        List<TransactionResponse> data = pages.getContent().stream()
                .map(transactionMapper::toResponse)
                .toList();

        return PageResponse.<TransactionResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    public Long getCurrentUserId() {
        return userClient.getMyInfo().getResult().getId();
    }

}
