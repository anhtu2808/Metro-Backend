package org.example.paymentservice.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import org.example.paymentservice.dto.request.PaymentRequest;
import org.example.paymentservice.dto.response.PaymentResponse;
import org.example.paymentservice.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface PaymentMapper extends EntityMapper<Payment, PaymentRequest, PaymentResponse> {
}
