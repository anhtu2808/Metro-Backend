package com.metro.content.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.content.dto.request.ContentRequest;
import com.metro.content.dto.request.ContentUpdateRequest;
import com.metro.content.dto.response.ContentResponse;
import com.metro.content.entity.Content;
import com.metro.content.entity.ContentImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring",
        config = DefaultConfigMapper.class
)
public interface ContentMapper{

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Content oldEntity, Content newEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapImageUrls")
    Content toEntity(ContentRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapImageUrls")
    Content updateToEntity(ContentUpdateRequest request);

    @Mapping(target = "imageUrls", source = "images", qualifiedByName = "mapImagesToUrls")
    ContentResponse toResponse(Content content);

    @Named("mapImageUrls")
    default List<ContentImage> mapImageUrls(List<String> urls) {
        if (urls == null) return null;
        return urls.stream()
                .map(url -> ContentImage.builder().imageUrl(url).build())
                .toList();
    }

    @Named("mapImagesToUrls")
    default List<String> mapImagesToUrls(List<ContentImage> images) {
        if (images == null) return null;
        return images.stream()
                .map(ContentImage::getImageUrl)
                .toList();
    }
}
