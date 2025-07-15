package com.metro.content.controller;

import com.metro.common_lib.controller.AbstractController;
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
    public ContentResponse createContent(@Valid @RequestBody ContentRequest request) {
        return contentService.createContent(request);
    }

    @GetMapping("/{id}")
    public ContentResponse getContentById(@PathVariable Long id) {
        return contentService.getContentById(id);
    }

    @GetMapping
    public PageResponse<ContentResponse> getContents(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(required = false) String sort) {
        return contentService.getAllContents(page, size, sort);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
    }

    @GetMapping("/by-type")
    public PageResponse<ContentResponse> getContentByType(@RequestParam ContentType type,@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        return contentService.getContentByType(type, page, size);
    }

}
