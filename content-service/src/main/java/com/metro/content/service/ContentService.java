package com.metro.content.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.content.dto.request.ContentRequest;
import com.metro.content.dto.request.ContentUpdateRequest;
import com.metro.content.dto.response.ContentResponse;
import com.metro.content.enums.ContentType;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ContentService {
    @PreAuthorize("hasAuthority('CONTENT_CREATE')")
    ContentResponse createContent(ContentRequest request);

    @PreAuthorize("hasAuthority('CONTENT_UPDATE')")
    ContentResponse updateContent(Long id, ContentUpdateRequest request);

    ContentResponse getContentById(Long id);

    PageResponse<ContentResponse> getAllContents(int page, int size, String arrange);

    @PreAuthorize("hasAuthority('CONTENT_DELETE')")
    void deleteContent(Long id);

    PageResponse<ContentResponse> getContentByType(ContentType type, int page, int size);
}
