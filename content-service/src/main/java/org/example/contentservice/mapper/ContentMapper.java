package org.example.contentservice.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import org.example.contentservice.dto.request.ContentRequest;
import org.example.contentservice.dto.response.ContentResponse;
import org.example.contentservice.entity.Content;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface ContentMapper  extends EntityMapper<Content, ContentRequest, ContentResponse> {
}
