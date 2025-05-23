package com.metro.api_gateway.service;

import com.metro.api_gateway.dto.response.IntrospectResponse;
import com.metro.common_lib.dto.response.ApiResponse;
import reactor.core.publisher.Mono;

public interface IdentityService {
    Mono<ApiResponse<IntrospectResponse>> introspect(String token);


}
