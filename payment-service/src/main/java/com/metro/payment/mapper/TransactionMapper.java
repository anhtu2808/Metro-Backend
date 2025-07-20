package com.metro.payment.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.payment.dto.response.TransactionResponse;
import com.metro.payment.entity.Transaction;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring",
        config = DefaultConfigMapper.class
)
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
