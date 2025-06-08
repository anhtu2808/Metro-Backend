package com.metro.api_gateway.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.metro.api_gateway.service.IdentityService;
import com.metro.common_lib.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    ObjectMapper objectMapper;

    @NonFinal
    private String[] publicUserServiceEndpoint = {
           "/register",
    }; // Khi nào thêm service khác muốn một số public thì khai báo thêm

    @NonFinal
    private String[] publicAuthEndpoint = {
            "/login",
            "/introspect",
            "/register",
    };
    @NonFinal
    private String[] publicRouteEndpoint = {
    };

    @NonFinal
    private String[] publicRoleEndpoint = {
            "/roles",
            "/permissions",
    };

    @NonFinal
    private String[] publicCommonEndpoint = {
            "/v3/api-docs",
            "/health-check",
    };

    private String apiPrefix = "/api/v1";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isPublicEndpoint(exchange.getRequest(), "/users", publicUserServiceEndpoint)) {
            return chain.filter(exchange);
        } // Khi nào thêm service khác muốn một số public thì khai báo thêm

        if (isPublicEndpoint(exchange.getRequest(), "/auth", publicAuthEndpoint)) {
            return chain.filter(exchange);
        }

        if (isPublicEndpoint(exchange.getRequest(), "", publicRoleEndpoint)) {
            return chain.filter(exchange);
        }

        if (isPublicCommonEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return unauthenticated(exchange.getResponse());

        String token = authHeader.getFirst().replace("Bearer ", "");

        return identityService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getResult().isValid())
                return chain.filter(exchange);
            else
                return unauthenticated(exchange.getResponse());
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request, String servicePath, String[] publicEndpoints) {
        String requestPath = request.getURI().getPath();
        System.out.println("Request Path: " + requestPath); // Log the request path
        return Arrays.stream(publicEndpoints).anyMatch(
                endpoint -> (requestPath.matches(apiPrefix + servicePath + endpoint))
        );
    }

    private boolean isPublicCommonEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return Arrays.stream(publicCommonEndpoint)
                .anyMatch(path::contains);
    }


    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Unauthenticated")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
