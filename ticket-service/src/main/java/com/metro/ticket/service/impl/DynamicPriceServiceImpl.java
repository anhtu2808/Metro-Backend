package com.metro.ticket.service.impl;

import com.metro.ticket.dto.response.DynamicPriceResponse;
import com.metro.ticket.dto.response.LineSegmentResponse;
import com.metro.ticket.entity.DynamicPrice;
import com.metro.ticket.entity.DynamicPriceMaster;
import com.metro.ticket.client.RouteServiceClient;
import com.metro.ticket.exception.AppException;
import com.metro.ticket.exception.ErrorCode;
import com.metro.ticket.mapper.DynamicPriceMapper;
import com.metro.ticket.repository.DynamicPriceMasterRepository;
import com.metro.ticket.repository.DynamicPriceRepository;
import com.metro.ticket.service.DynamicPriceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicPriceServiceImpl implements DynamicPriceService {

    DynamicPriceMasterRepository dynamicPriceMasterRepository;
    RouteServiceClient routeServiceClient;
    DynamicPriceRepository dynamicPriceRepository;
    DynamicPriceMapper dynamicPriceMapper;

    @Override
    public List<DynamicPriceResponse> calculateDynamicPriceById(Long lineId) {
        DynamicPriceMaster rule = dynamicPriceMasterRepository.findByLineId(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.DYNAMIC_PRICE_MASTER_NOT_FOUND));

        List<LineSegmentResponse> segments = routeServiceClient.getSegmentsByLine(lineId).getResult();
        if (segments.isEmpty()) {
            throw new AppException(ErrorCode.LINE_SEGMENT_NOT_FOUND);
        }
        // Delete existing dynamic prices for the line
        dynamicPriceRepository.deleteByLineId(lineId);

        List<DynamicPrice> prices = new ArrayList<>();
        // Calculate prices  for segments in forward order
        for (int i = 0; i < segments.size(); i++) {
            float distanceSum = 0;
            long startStationId = segments.get(i).getStartStationId();
            for (int j = i; j < segments.size(); j++) {
                LineSegmentResponse seg = segments.get(j);
                distanceSum += seg.getDistance();
                long endStationId = seg.getEndStationId();

                calculateDynamicPriceRule(lineId, rule, prices, distanceSum, startStationId, endStationId);
            }
        }
        // Calculate prices for segments in reverse order
        for (int i = segments.size() - 1; i >= 0; i--) {
            float distanceSum = 0;
            long startStationId = segments.get(i).getEndStationId();
            for (int j = i; j >= 0; j--) {
                LineSegmentResponse seg = segments.get(j);
                distanceSum += seg.getDistance();
                long endStationId = seg.getStartStationId();

                calculateDynamicPriceRule(lineId, rule, prices, distanceSum, startStationId, endStationId);
            }
        }

        dynamicPriceRepository.saveAll(prices);

        return prices.stream()
                .map(dynamicPriceMapper::toDynamicPriceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DynamicPriceResponse> getDynamicPricesByLineId(Long lineId) {
        List<DynamicPrice> prices = dynamicPriceRepository.findByLineId(lineId);
        if (prices.isEmpty()) {
            throw new AppException(ErrorCode.DYNAMIC_PRICE_NOT_FOUND);
        }
        return prices.stream()
                .map(dynamicPriceMapper::toDynamicPriceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DynamicPriceResponse> getDynamicPricesByLineIdAndStartStationId(Long lineId, Long startStationId) {
        List<DynamicPrice> prices = dynamicPriceRepository.findByLineIdAndStartStationId(lineId, startStationId);
        if (prices.isEmpty()) {
            throw new AppException(ErrorCode.DYNAMIC_PRICE_NOT_FOUND);
        }
        return prices.stream()
                .map(dynamicPriceMapper::toDynamicPriceResponse)
                .collect(Collectors.toList());
    }

    private void calculateDynamicPriceRule(Long lineId, DynamicPriceMaster rule, List<DynamicPrice> prices, float distanceSum, long startStationId, long endStationId) {
        BigDecimal distance = BigDecimal.valueOf(distanceSum);
        BigDecimal price = rule.getStartPrice();
        if (distance.compareTo(rule.getStartRange()) > 0) {
            BigDecimal extra = distance.subtract(rule.getStartRange());
            price = price.add(extra.multiply(rule.getPricePerKm()));
            price = price.setScale(0, RoundingMode.FLOOR);
        }

        prices.add(DynamicPrice.builder()
                .startStationId(startStationId)
                .endStationId(endStationId)
                .lineId(lineId)
                .price(price)
                .dynamicPriceMaster(rule)
                .build());
    }

    @Override
    public DynamicPriceResponse getPriceByStartAndEnd(Long lineId, Long startStationId, Long endStationId) {
        DynamicPrice price = dynamicPriceRepository
                .findByLineIdAndStartStationIdAndEndStationId(lineId, startStationId, endStationId)
                .orElseThrow(() -> new AppException(ErrorCode.DYNAMIC_PRICE_NOT_FOUND));
        return dynamicPriceMapper.toDynamicPriceResponse(price);
    }
}
