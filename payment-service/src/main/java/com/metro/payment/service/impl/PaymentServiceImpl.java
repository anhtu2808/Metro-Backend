package com.metro.payment.service.impl;


import com.metro.payment.configuration.VNPAYConfig;
import com.metro.payment.dto.response.VNPayResponse;
import com.metro.payment.entity.Transaction;
import com.metro.payment.enums.PaymentMethodEnum;
import com.metro.payment.enums.PaymentStatusEnum;
import com.metro.payment.enums.TicketStatus;
import com.metro.payment.exception.AppException;
import com.metro.payment.exception.ErrorCode;
import com.metro.payment.repository.TransactionRepository;
import com.metro.payment.repository.httpClient.TicketOrderClient;
import com.metro.payment.repository.httpClient.UserClient;
import com.metro.payment.service.PaymentService;
import com.metro.payment.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentServiceImpl implements PaymentService {
    final VNPAYConfig vnPayConfig;
    final TransactionRepository transactionRepository;
    final TicketOrderClient ticketOrderClient;
    final UserClient userClient;
    @Value("${internal.secret}")
    String internalSecret;

    private Long getCurrentUserId() {
        try {
            return userClient.getMyInfo().getResult().getId();
        } catch (Exception e) {
            log.error("Lỗi khi lấy userId từ UserClient", e);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }


    @Override
    public VNPayResponse createVNPayPayment(Long ticketOrderId, String bankCode, HttpServletRequest request) {
        Long userId = getCurrentUserId();

        var ticketOrder = ticketOrderClient.getTicketOrderById(ticketOrderId).getResult();
        if (ticketOrder == null || ticketOrder.getPrice() == null) {
            throw new AppException(ErrorCode.TICKET_ORDER_NOT_EXISTED);
        }
        
        BigDecimal amount = ticketOrder.getPrice();
        String transactionCode = VNPayUtil.getRandomNumber(10);
        long vnpAmount = amount.multiply(BigDecimal.valueOf(100)).longValue();

        // 🟢 Lưu giao dịch trước với PENDING
        transactionRepository.save(Transaction.builder()
                .sagaId(null) // Saga ID can be set later if needed
                .transactionCode(transactionCode)
                .amount(amount)
                .status(PaymentStatusEnum.PENDING)
                .paymentMethod(PaymentMethodEnum.VNPAY)
                .orderTicketId(ticketOrderId)
                .userId(userId)
                .build());

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(vnpAmount));
        vnpParamsMap.put("vnp_TxnRef", transactionCode);
        vnpParamsMap.put("vnp_OrderInfo", ticketOrderId.toString()); // chỉ cần ticketOrderId
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    @Override
    public VNPayResponse handleVNPayCallback(HttpServletRequest request) {
        String responseCode = request.getParameter("vnp_ResponseCode");
        String transactionCode = request.getParameter("vnp_TxnRef");
        LocalDateTime purchaseDate = LocalDateTime.now();
        String formattedPurchaseDate = purchaseDate.format(DateTimeFormatter.ISO_DATE_TIME);

        try {
            var transactionOpt = transactionRepository.findByTransactionCode(transactionCode);
            if (transactionOpt.isEmpty()) {
                return new VNPayResponse("99", "Giao dịch không tồn tại", null);
            }

            Transaction transaction = transactionOpt.get();

            if (transaction.getStatus() == PaymentStatusEnum.SUCCESS) {
                return new VNPayResponse("00", "Giao dịch đã được xử lý", null);
            }

            if ("00".equals(responseCode)) {
                transaction.setStatus(PaymentStatusEnum.SUCCESS);
                transactionRepository.save(transaction);
                try {
                    log.info("🔥 Sending secret: {}", internalSecret);
                    if (transaction.getSagaId() == null) {
                        ticketOrderClient.updateTicketOrderStatus(transaction.getOrderTicketId(), TicketStatus.INACTIVE, formattedPurchaseDate, internalSecret);
                    }else {
                        ticketOrderClient.handleAdjustmentCallback
                                (transaction.getSagaId(),
                                        true,
                                        "Thanh toán thành công",
                                        internalSecret);  // Sync call to Order (add FeignClient for Order if needed)
                    }
                } catch (Exception ex) {
                    log.error("Không thể cập nhật trạng thái TicketOrder sau khi thanh toán", ex);
                }

                return new VNPayResponse("00", "Thanh toán thành công", null);
            } else {
                transaction.setStatus(PaymentStatusEnum.FAILED);
                transactionRepository.save(transaction);
                return new VNPayResponse("99", "Thanh toán thất bại", null);
            }
        } catch (Exception e) {
            log.error("Lỗi xử lý callback VNPay", e);
            return new VNPayResponse("99", "Lỗi xử lý callback", null);
        }
    }
    @Override
    public VNPayResponse createAdjustmentPayment(
            Long sagaId,
            Long ticketOrderId,
            BigDecimal adjustmentAmount,
            String bankCode,
            HttpServletRequest request
            ) {
        Long userId = getCurrentUserId();

        var ticketOrder = ticketOrderClient.getTicketOrderById(ticketOrderId).getResult();
        if (ticketOrder == null || ticketOrder.getPrice() == null) {
            throw new AppException(ErrorCode.TICKET_ORDER_NOT_EXISTED);
        }

        String transactionCode = VNPayUtil.getRandomNumber(10);
        long vnpAmount = adjustmentAmount.multiply(BigDecimal.valueOf(100)).longValue();

        // 🟢 Lưu giao dịch trước với PENDING
        transactionRepository.save(Transaction.builder()
                .sagaId(sagaId)
                .transactionCode(transactionCode)
                .amount(adjustmentAmount)
                .status(PaymentStatusEnum.PENDING)
                .paymentMethod(PaymentMethodEnum.VNPAY)
                .orderTicketId(ticketOrderId)
                .userId(userId)
                .build());
//        HttpServletRequest request = ipAddr;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(vnpAmount));
        vnpParamsMap.put("vnp_TxnRef", transactionCode);
        vnpParamsMap.put("vnp_OrderInfo", ticketOrderId.toString()); // chỉ cần ticketOrderId
//        vnpParamsMap.put("vnp_IpAddr",ipAddr);
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }
}
