package com.metro.common_lib.service;

import com.metro.common_lib.dto.response.PageResponse;

public interface IService<E, C, U, R> {
    R create(C createRequest);

    R findById(Long id);

    PageResponse<R> findAll(int page, int size, String sort);

    void delete(Long id);

    R update(Long id, U updateRequest);
}
