package com.metro.content.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.content.dto.request.ContentRequest;
import com.metro.content.dto.response.ContentResponse;
import com.metro.content.entity.Content;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface ContentMapper  extends EntityMapper<Content, ContentRequest, ContentResponse> {
}
