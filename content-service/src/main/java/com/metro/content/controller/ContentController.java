package com.metro.content.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.service.AbstractService;
import com.metro.content.dto.request.ContentRequest;
import com.metro.content.dto.request.ContentUpdateRequest;
import com.metro.content.dto.response.ContentResponse;
import com.metro.content.entity.Content;
import com.metro.content.service.ContentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contents")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentController extends AbstractController<
        Content,
        ContentRequest,
        ContentUpdateRequest,
        ContentResponse
        > {

    public ContentController(AbstractService<Content, ContentRequest, ContentUpdateRequest, ContentResponse> service) {
        super(service);
    }

}
