package com.metro.payment.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.payment.dto.request.PaymentRequest;
import com.metro.payment.dto.response.PaymentResponse;
import com.metro.payment.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface PaymentMapper extends EntityMapper<Payment, PaymentRequest, PaymentResponse> {

}
