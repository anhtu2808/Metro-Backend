package com.metro.common_lib.mapper;

import java.util.List;

public interface EntityMappers<E, CRE, UP, RP> {
    E toEntity(CRE request);
    void updateEntity(E entity, UP request);
    RP toResponse(E entity);
    List<E> toEntity(List<CRE> requestList);
    List<RP> toResponse(List<E> entityList);
}