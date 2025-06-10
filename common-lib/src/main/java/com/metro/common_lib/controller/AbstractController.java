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
        try {
            R response = service.create(request);
            return ApiResponse.<R>builder()
                    .result(response)
                    .message("Created successfully")
                    .code(201)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<R>builder()
                    .result(null)
                    .code(500)
                    .message("Error creating entity: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse<R> findById(Long id) {
        try {
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
        } catch (Exception e) {
            return ApiResponse.<R>builder()
                    .result(null)
                    .code(500)
                    .message("Error finding entity: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse<PageResponse<R>> findAll(int page, int size, String sort) {
        try {
            PageResponse<R> response = service.findAll(page, size, sort);
            return ApiResponse.<PageResponse<R>>builder()
                    .result(response)
                    .message("Entities found")
                    .code(200)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<PageResponse<R>>builder()
                    .result(null)
                    .code(500)
                    .message("Error finding entities: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse<R> update(Long id, U updateRequest) {
        try {
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
        } catch (Exception e) {
            return ApiResponse.<R>builder()
                    .result(null)
                    .code(500)
                    .message("Error updating entity: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        try {
            service.delete(id);
            return ApiResponse.<Void>builder()
                    .result(null)
                    .message("Deleted successfully")
                    .code(204)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .result(null)
                    .code(500)
                    .message("Error deleting entity: " + e.getMessage())
                    .build();
        }
    }
}
