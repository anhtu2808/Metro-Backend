package com.metro.common_lib.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;

public abstract class AbstractController<E, C, U, R> implements IController<E, C, U, R> {
    protected AbstractService<E, C, U, R> service;

    public AbstractController(AbstractService<E, C, U, R> service) {
        this.service = service;
    }

    @Override
    public ApiResponse<R> create(C request) {
        R response = service.create(request);
        return ApiResponse.<R>builder()
                .result(response)
                .message("Created successfully")
                .code(201)
                .build();
    }

    @Override
    public ApiResponse<R> findById(Long id) {
        R response = service.findById(id);
        if (response == null) {
            return ApiResponse.<R>builder()
                    .result(null)
                    .code(404)
                    .message("Entity not found")
                    .build();
        }
        return ApiResponse.<R>builder()
                .result(response)
                .message("Entity found")
                .code(200)
                .build();
    }

    @Override
    public ApiResponse<PageResponse<R>> findAll(int page, int size, String sort) {
        PageResponse<R> response = service.findAll(page, size, sort);
        return ApiResponse.<PageResponse<R>>builder()
                .result(response)
                .message("Entities found")
                .code(200)
                .build();
    }

    @Override
    public ApiResponse<R> update(Long id, U updateRequest) {
        R response = service.update(id, updateRequest);
        if (response == null) {
            return ApiResponse.<R>builder()
                    .result(null)
                    .code(404)
                    .message("Entity not found")
                    .build();
        }
        return ApiResponse.<R>builder()
                .result(response)
                .message("Updated successfully")
                .code(200)
                .build();
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        service.delete(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .message("Deleted successfully")
                .code(204)
                .build();
    }
}
