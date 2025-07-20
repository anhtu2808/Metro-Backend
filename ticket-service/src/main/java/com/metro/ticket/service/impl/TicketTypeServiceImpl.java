package com.metro.ticket.service.impl;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.metro.ticket.exception.AppException;
import com.metro.ticket.exception.ErrorCode;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.ticket.dto.request.ticketType.TicketTypeCreationRequest;
import com.metro.ticket.dto.request.ticketType.TicketTypeUpdateRequest;
import com.metro.ticket.dto.response.TicketTypeResponse;
import com.metro.ticket.entity.TicketType;
import com.metro.ticket.mapper.TicketTypeMapper;
import com.metro.ticket.repository.TicketTypeRepository;
import com.metro.ticket.service.TicketTypeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class TicketTypeServiceImpl implements TicketTypeService{
    TicketTypeRepository ticketTypeRepository;
    TicketTypeMapper ticketTypeMapper;

    @Override
    public TicketTypeResponse createTicketType(TicketTypeCreationRequest request) {
        TicketType ticketType = ticketTypeMapper.toEntity(request);
        if (ticketType.getPrice() == null || ticketType.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_TICKET_PRICE);
        }
        if (ticketType.getName() == null || ticketType.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_TICKET_NAME);
        }
        if (ticketTypeRepository.existsByName(ticketType.getName())) {
            throw new AppException(ErrorCode.TICKET_NAME_ALREADY_EXISTS);
        }
        ticketType = ticketTypeRepository.save(ticketType);
        return ticketTypeMapper.toResponse(ticketType);
    }

    @Override
    public TicketTypeResponse getTicketTypeById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_TYPE_NOT_FOUND));
        return ticketTypeMapper.toResponse(ticketType);
    }

    @Override
    public PageResponse<TicketTypeResponse> getTicketTypes(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<TicketType> pages = ticketTypeRepository.findAll(pageable);
        List<TicketTypeResponse> data = pages.getContent().stream()
                .map(ticketTypeMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.<TicketTypeResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    public TicketTypeResponse updateTicketType(Long id, TicketTypeUpdateRequest request) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_TYPE_NOT_FOUND));
        ticketTypeMapper.updateEntity(request, ticketType);
        if (ticketType.getPrice() != null && ticketType.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_TICKET_PRICE);
        }
        if (ticketType.getName() != null && ticketType.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_TICKET_NAME);
        }
        ticketType = ticketTypeRepository.save(ticketType);
        return ticketTypeMapper.toResponse(ticketType);
    }

    @Override
    public void deleteTicketType(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_TYPE_NOT_FOUND));
        ticketTypeRepository.delete(ticketType);
    }
}