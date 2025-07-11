package com.metro.common_lib.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;

public interface IController<E, C, U, R> {
    @GetMapping("/{id}")
    ApiResponse<R> findById(@PathVariable("id") Long id);

    @GetMapping("")
    ApiResponse<PageResponse<R>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort);

    @PostMapping
    ApiResponse<R> create(@Valid @RequestBody C createRequest);

    @DeleteMapping("/{id}")
    ApiResponse<Void> delete(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    ApiResponse<R> update(@PathVariable Long id, @Valid @RequestBody U updateRequest);
}
