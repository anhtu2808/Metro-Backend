package com.metro.content.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.content.dto.request.ContentRequest;
import com.metro.content.dto.request.ContentUpdateRequest;
import com.metro.content.dto.response.ContentResponse;
import com.metro.content.entity.Content;
import com.metro.content.enums.ContentType;
import com.metro.content.service.ContentService;
import com.metro.content.service.impl.ContentServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contents")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ContentController{

    private final ContentService contentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ContentResponse> createContent(@Valid @RequestBody ContentRequest request) {
        ContentResponse response = contentService.createContent(request);
        return ApiResponse.<ContentResponse>builder()
                .result(response)
                .message("Content created successfully")
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ContentResponse> getContentById(@PathVariable Long id) {
        ContentResponse response = contentService.getContentById(id);
        return ApiResponse.<ContentResponse>builder()
                .result(response)
                .message("Get content by ID successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<ContentResponse>> getContents(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(required = false) String sort) {
        PageResponse<ContentResponse> response = contentService.getAllContents(page, size, sort);
        return ApiResponse.<PageResponse<ContentResponse>>builder()
                .result(response)
                .message("Get all contents successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
    }

    @GetMapping("/by-type")
    public ApiResponse<PageResponse<ContentResponse>> getContentByType(@RequestParam ContentType type,@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        PageResponse<ContentResponse> response = contentService.getContentByType(type, page, size);
        return ApiResponse.<PageResponse<ContentResponse>>builder()
                .result(response)
                .message("Get contents by type successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ContentResponse> updateContent(
            @PathVariable Long id,
            @Valid @RequestBody ContentUpdateRequest request) {
        ContentResponse response = contentService.updateContent(id, request);
        return ApiResponse.<ContentResponse>builder()
                .result(response)
                .message("Content updated successfully")
                .code(HttpStatus.OK.value())
                .build();
    }
}
