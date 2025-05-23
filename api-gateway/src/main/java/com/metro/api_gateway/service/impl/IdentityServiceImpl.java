package com.metro.api_gateway.service.impl;

import com.metro.api_gateway.dto.request.IntrospectRequest;
import com.metro.api_gateway.dto.response.IntrospectResponse;
import com.metro.api_gateway.repository.IdentityClient;
import com.metro.api_gateway.service.IdentityService;
import com.metro.common_lib.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityServiceImpl implements IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){
        return identityClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}
