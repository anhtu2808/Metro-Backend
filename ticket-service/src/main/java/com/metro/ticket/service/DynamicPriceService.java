package com.metro.ticket.service;


import com.metro.ticket.dto.response.DynamicPriceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicPriceService {
    List<DynamicPriceResponse> calculateDynamicPriceById(Long lineId);

    List<DynamicPriceResponse> getDynamicPricesByLineId(Long lineId);

    List<DynamicPriceResponse> getDynamicPricesByLineIdAndStartStationId(Long lineId, Long startStationId);
    DynamicPriceResponse getPriceByStartAndEnd(Long lineId, Long startStationId, Long endStationId);

}
