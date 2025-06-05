package com.metro.common_lib.mapper;

import java.util.List;

public interface EntityMappers<E, CRE, UP, RP> {
    E toEntity(CRE creationRequest);
    E updateFrom(UP updateRequest);
    RP toResponse(E entity);
    List<E> toEntity(List<CRE> creationRequestList);
    List<RP> toResponse(List<E> entityList);
}