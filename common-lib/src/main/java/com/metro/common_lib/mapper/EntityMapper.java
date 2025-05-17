package com.metro.common_lib.mapper;

import org.mapstruct.*;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto );

    D toDto(E entity);

    List<D> toDto(List<E> entities);

    List<E> toEntity(List<D> dtos);
}
