package com.metro.content.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.content.dto.request.ContentRequest;
import com.metro.content.dto.request.ContentUpdateRequest;
import com.metro.content.dto.response.ContentResponse;
import com.metro.content.entity.Content;
import com.metro.content.entity.ContentImage;
import com.metro.content.enums.ContentType;
import com.metro.content.mapper.ContentMapper;
import com.metro.content.repository.ContentImageRepository;
import com.metro.content.repository.ContentRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentService extends AbstractService<Content, ContentRequest, ContentUpdateRequest, ContentResponse> {

    ContentImageRepository contentImageRepository;
    ContentMapper contentMapper;

    public ContentService(ContentRepository repository, ContentMapper contentMapper, ContentImageRepository contentImageRepository) {
        super(repository, contentMapper);
        this.contentImageRepository = contentImageRepository;
        this.contentMapper = contentMapper;
    }

    @Override
    protected void beforeCreate(Content entity) {
        List<ContentImage> images = new ArrayList<>();
        if (entity.getImages() != null) {
            for (ContentImage image : entity.getImages()) {
                image.setContent(entity);
                images.add(image);
            }
        }
        entity.setImages(images);
    }

    @Override
    protected void beforeUpdate(Content oldEntity, Content newEntity) {
        oldEntity.setTitle(newEntity.getTitle());
        oldEntity.setBody(newEntity.getBody());
        oldEntity.setSummary(newEntity.getSummary());
        oldEntity.setStatus(newEntity.getStatus());
        oldEntity.setType(newEntity.getType());
        oldEntity.setImageUrl(newEntity.getImageUrl());
        oldEntity.setPublishAt(newEntity.getPublishAt());

        oldEntity.getImages().clear();

        List<ContentImage> updatedImages = newEntity.getImages().stream()
                .peek(img -> img.setContent(oldEntity))
                .toList();

        oldEntity.getImages().addAll(updatedImages);
    }


    @Override
    @PreAuthorize("hasAuthority('content:create')")
    public ContentResponse create(ContentRequest request) {
        return super.create(request);
    }

    @Override
    public ContentResponse findById(Long id) {
        return super.findById(id);
    }

    @Override
    public PageResponse<ContentResponse> findAll(int page, int size, String arrange) {
        return super.findAll(page, size, arrange);
    }

    @Override
    @PreAuthorize("hasAuthority('content:update')")
    public ContentResponse update(Long id, ContentUpdateRequest request) {
        return super.update(id, request);
    }

    @Override
    @PreAuthorize("hasAuthority('content:delete')")
    public void delete(Long id) {
        super.delete(id);
    }

    public PageResponse<ContentResponse> getContentByType(ContentType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Content> contentPage = ((ContentRepository) repository).findByType(type, pageable);
        List<ContentResponse> contentResponses = contentPage.getContent().stream()
                .map(contentMapper::toResponse)
                .toList();
        return PageResponse.<ContentResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(contentPage.getTotalPages())
                .totalElements(contentPage.getTotalElements())
                .data(contentResponses)
                .build();
    }

}
