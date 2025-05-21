package com.metro.common_lib.mapper;

import java.util.List;


public interface EntityMapper<E,RE,RP> {
    E toEntity(RE request);
    RP toResponse(E entity);
    List<E> toEntity(List<RE> requestList);
    List<RE> toRequest(List<E> entityList);
}
