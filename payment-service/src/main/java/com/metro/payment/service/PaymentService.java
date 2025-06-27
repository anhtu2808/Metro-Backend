package com.metro.payment.service;


import com.metro.payment.configuration.VNPAYConfig;
import com.metro.payment.dto.response.VNPayResponse;
import com.metro.payment.entity.Transaction;
import com.metro.payment.enums.PaymentMethodEnum;
import com.metro.payment.enums.PaymentStatusEnum;
import com.metro.payment.repository.TransactionRepository;
import com.metro.payment.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    private final VNPAYConfig vnPayConfig;
    TransactionRepository transactionRepository;

    private Long getCurrentUserId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(jwt.getClaimAsString("userId")); // hoặc getSubject() nếu userId nằm ở sub
    }


    public VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        String ticketOrderId = request.getParameter("ticketOrderId");
        String transactionCode = VNPayUtil.getRandomNumber(10);

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_TxnRef", transactionCode);
        vnpParamsMap.put("vnp_OrderInfo", ticketOrderId);
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

    public VNPayResponse handleCallback(HttpServletRequest request) {
        String responseCode = request.getParameter("vnp_ResponseCode");
        String transactionCode = request.getParameter("vnp_TxnRef");
        String amountStr = request.getParameter("vnp_Amount");
        String orderIdStr = request.getParameter("vnp_OrderInfo");
        Long userId = getCurrentUserId();

        if ("00".equals(responseCode) && !transactionRepository.existsByTransactionCode(transactionCode)) {
            Transaction transaction = Transaction.builder()
                    .transactionCode(transactionCode)
                    .amount(new BigDecimal(Long.parseLong(amountStr) / 100))
                    .status(PaymentStatusEnum.SUCCESS)
                    .paymentMethod(PaymentMethodEnum.VNPAY)
                    .orderTicketId(Long.parseLong(orderIdStr))
                    .userId(userId) // TODO: sau này lấy từ token hoặc request
                    .build();
            transactionRepository.save(transaction);
            return new VNPayResponse("00", "Thanh toán thành công", null);
        } else {
            return new VNPayResponse("99", "Thanh toán thất bại hoặc đã tồn tại", null);
        }
    }
}
