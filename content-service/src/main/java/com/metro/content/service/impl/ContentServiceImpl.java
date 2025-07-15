package com.metro.content.service.impl;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.content.dto.request.ContentRequest;
import com.metro.content.dto.request.ContentUpdateRequest;
import com.metro.content.dto.response.ContentResponse;
import com.metro.content.entity.Content;
import com.metro.content.entity.ContentImage;
import com.metro.content.enums.ContentType;
import com.metro.content.exception.AppException;
import com.metro.content.exception.ErrorCode;
import com.metro.content.mapper.ContentMapper;
import com.metro.content.repository.ContentImageRepository;
import com.metro.content.repository.ContentRepository;
import com.metro.content.service.ContentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentServiceImpl implements ContentService {

    ContentImageRepository contentImageRepository;
    ContentMapper contentMapper;
    ContentRepository contentRepository;

    @Override
    public ContentResponse createContent(ContentRequest request) {
        Content entity = contentMapper.toEntity(request);
        if (entity.getImages() != null) {
            entity.getImages().forEach(img -> img.setContent(entity));
        }
        Content saved = contentRepository.save(entity);
        return contentMapper.toResponse(saved);
    }

    @Override
    public ContentResponse updateContent(Long id, ContentUpdateRequest request) {
        Content oldEntity = contentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONTENT_NOT_FOUND));

        Content newEntity = contentMapper.updateToEntity(request);
        contentMapper.updateEntity(oldEntity, newEntity);
        oldEntity.getImages().clear();
        if (newEntity.getImages() != null) {
            List<ContentImage> updatedImages = newEntity.getImages().stream()
                    .peek(img -> img.setContent(oldEntity))
                    .toList();
            oldEntity.getImages().addAll(updatedImages);
        }
        Content updated = contentRepository.save(oldEntity);
        return contentMapper.toResponse(updated);
    }

    @Override
    public ContentResponse getContentById(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONTENT_NOT_FOUND));
        return contentMapper.toResponse(content);
    }

    @Override
    public PageResponse<ContentResponse> getAllContents(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        Page<Content> contentPage = contentRepository.findAll(pageable);
        List<ContentResponse> responses = contentPage.getContent().stream()
                .map(contentMapper::toResponse)
                .toList();

        return PageResponse.<ContentResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(contentPage.getTotalPages())
                .totalElements(contentPage.getTotalElements())
                .data(responses)
                .build();
    }

    @Override
    public void deleteContent(Long id) {
        if (!contentRepository.existsById(id)) {
            throw new AppException(ErrorCode.CONTENT_NOT_FOUND);
        }
        contentRepository.deleteById(id);
    }

    @Override
    public PageResponse<ContentResponse> getContentByType(ContentType type, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        Page<Content> contentPage = contentRepository.findByType(type, pageable);
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
