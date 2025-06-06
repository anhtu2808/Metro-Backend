package com.metro.order.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.order.dto.request.OrderRequest;
import com.metro.order.dto.response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface OrderMapper extends EntityMapper<Order, OrderRequest, OrderResponse> {
}
