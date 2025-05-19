package com.metro.common_lib.mapper;

import java.util.List;

import org.mapstruct.*;

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDto(List<E> entities);

    List<E> toEntity(List<D> dtos);
}
