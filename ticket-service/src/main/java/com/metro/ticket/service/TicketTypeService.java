package com.metro.ticket.service;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.ticket.dto.request.ticketType.*;
import com.metro.ticket.dto.response.TicketTypeResponse;
import com.metro.ticket.entity.TicketType;
import com.metro.ticket.exception.AppException;
import com.metro.ticket.exception.ErrorCode;
import com.metro.ticket.mapper.TicketTypeMapper;
import com.metro.ticket.repository.TicketTypeRepository;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketTypeService extends AbstractService<
        TicketType,
        TicketTypeCreationRequest,
        TicketTypeUpdateRequest,
        TicketTypeResponse> {

    private final TicketTypeMapper ticketTypeMapper;

    protected TicketTypeService(TicketTypeRepository repository, TicketTypeMapper entityMapper, TicketTypeMapper ticketTypeMapper) {
        super(repository, entityMapper);
        this.ticketTypeMapper = ticketTypeMapper;
    }
    @Override
    @PreAuthorize("hasAuthority('ticketType:create')")
    public TicketTypeResponse create(TicketTypeCreationRequest request) {
        log.info("Creating TicketType with request: {}", request);
        return super.create(request);
    }

    @Override
    @PreAuthorize("hasAuthority('ticketType:read')")
    public TicketTypeResponse findById(Long id) {
        log.info("Finding TicketType with ID: {}", id);
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ticketType:read')")
    public PageResponse<TicketTypeResponse> findAll(int page, int size, String arrange) {
        log.info("Finding all TicketTypes with page: {}, size: {}, arrange: {}", page, size, arrange);
        return super.findAll(page, size, arrange);
    }

    @Override
    @PreAuthorize("hasAuthority('ticketType:update')")
    public TicketTypeResponse update(Long id, TicketTypeUpdateRequest request) {
        log.info("Updating TicketType with ID: {} and request: {}", id, request);
        return super.update(id, request);
    }

    @Override
    @PreAuthorize("hasAuthority('ticketType:delete')")
    public void delete(Long id) {
        log.info("Deleting TicketType with ID: {}", id);
        super.delete(id);
    }

    @Override
    protected void beforeCreate(TicketType entity) {
        if (entity.getPrice() == null || entity.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_TICKET_PRICE);
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_TICKET_NAME);
        }
    }
    @Override
    protected void beforeUpdate(TicketType oldEntity, TicketType newEntity) {
        if (newEntity.getPrice() != null && newEntity.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_TICKET_PRICE);
        }
        if (newEntity.getName() != null && newEntity.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_TICKET_NAME);
        }
        ticketTypeMapper.updateEntity(oldEntity, newEntity);
    }
}