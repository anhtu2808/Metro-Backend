package com.metro.common_lib.mapper;

import java.util.List;

public interface EntityMappers<E, C, U, R> {
    E toEntity(C createDto);

    E updateToEntity(U updateDto);

    R toResponse(E entity);

    List<E> toEntityList(List<C> createDto);

    List<R> toResponseList(List<E> entities);
}
