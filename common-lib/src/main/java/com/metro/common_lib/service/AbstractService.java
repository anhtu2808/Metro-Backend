package com.metro.common_lib.service;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.exception.AppException;
import com.metro.common_lib.exception.ErrorCode;
import com.metro.common_lib.mapper.EntityMappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Slf4j
public abstract class AbstractService<
        E ,
        C ,
        U ,
        R>
        implements IService<E, C, U, R> {

    protected  JpaRepository<E, Long> repository;
    protected  EntityMappers<E, C, U, R> entityMapper;

    protected AbstractService(JpaRepository<E, Long> repository,
                              EntityMappers<E, C, U, R> entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    protected abstract void beforeCreate(E entity);
    protected abstract void beforeUpdate(E oldEntity, E newEntity);

    @Override
    public R create(C request) {
        log.info("Creating new entity");
        E entity = entityMapper.toEntity(request);
        beforeCreate(entity);
        entity = repository.save(entity);
        return entityMapper.toResponse(entity);
    }

    @Override
    public PageResponse<R> findAll(int page, int size, String arrange) {
        log.info("Fetching all entities");
        List<E> entities = repository.findAll();
        if (arrange == null || arrange.isEmpty()) {
            arrange = "id";
        }
        Sort sort = Sort.by(Sort.Direction.ASC, arrange);
        Pageable pageable = PageRequest.of(page - 1, size,sort);
        var pageData = repository.findAll(pageable);
        var postList = pageData.getContent().stream().map(post -> {
            R R = entityMapper.toResponse(post);
            return R;
        }).toList();
            return PageResponse.<R>builder()
                    .currentPage(page)
                    .totalPages(pageData.getTotalPages())
                    .pageSize(size)
                    .totalElements(pageData.getTotalElements())
                    .data(postList)
                    .build();
    }

    @Override
    public R findById(Long id) {
        log.info("Fetching entity with ID: {}", id);
        E entity = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_FOUND));
        return entityMapper.toResponse(entity);
    }

    @Override
    public R update(Long id, U request) {
        log.info("Updating entity with ID: {}", id);
        E oldEntity = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_FOUND));
        E newEntity = entityMapper.updateToEntity(request);
        beforeUpdate(oldEntity, newEntity);
        oldEntity = repository.save(oldEntity);
        return entityMapper.toResponse(oldEntity);
    }

    @Override
    public Void delete(Long id) {
        log.info("Deleting entity with ID: {}", id);
        repository.deleteById(id);
        return null;
    }
}