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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    // 1. Định nghĩa prefix chung
    static final String API_PREFIX = "/api/v1";
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();


    IdentityService identityService;
    ObjectMapper objectMapper;

    // 2. Map HTTP method → danh sách các regex path public (chưa có prefix)
    @NonFinal
    Map<HttpMethod, List<String>> publicEndpointsByMethod = new HashMap<>() {{
        put(HttpMethod.GET, List.of(
                //TODO: thêm các endpoint public cho method GET nếu có
                "/contents/**",
                "/v3/api-docs/**",
                "/health-check",
                "/users/my-info",
                "/lines/**",
                "/stations/**",
                "/bus-routes/**",
                "/line-segments",
                "/ticket-types/**",
                "/payment/vnpay-callback"
        ));
        put(HttpMethod.POST, List.of(
                // TODO: thêm các endpoint public cho method POST nếu có
                "/users/register",
                "/auth/login",
                "/auth/introspect",
                "/auth/refresh",
                "/auth/oauth/google"
        ));
        put(HttpMethod.PUT, List.of(
                //TODO : thêm các endpoint public cho method PUT nếu có
        ));
    }};


    private final String[] publicCommonEndpoint = {
            "/v3/api-docs",
            "/health-check",
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String path = request.getURI().getPath();

        // 0. Bỏ qua authentication cho preflight requests
        if (method == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        // 3. Nếu là public endpoint theo method + path thì bỏ qua auth
        if (isPublicEndpoint(method, path)) {
            return chain.filter(exchange);
        }
        // 3.1 Nếu là public endpoint theo common path thì bỏ qua auth
        if (isPublicCommonEndpoint(request)) {
            return chain.filter(exchange);
        }

        // 4. Lấy token từ header
        List<String> authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader)) {
            return unauthenticated(exchange.getResponse());
        }

        String token = authHeader.get(0).replace("Bearer ", "");
        return identityService.introspect(token)
                .flatMap(resp -> resp.getResult().isValid()
                        ? chain.filter(exchange)
                        : unauthenticated(exchange.getResponse())
                )
                .onErrorResume(err -> unauthenticated(exchange.getResponse()));
    }

    /**
     * Kiểm tra xem request có phải public endpoint không:
     * - Lấy danh sách pattern theo HTTP method
     * - Với mỗi pattern, nối thêm API_PREFIX trước khi matches()
     */
    private boolean isPublicEndpoint(HttpMethod method, String path) {
        List<String> patterns = publicEndpointsByMethod.get(method);
        if (patterns == null || patterns.isEmpty()) {
            return false;
        }
        return patterns.stream()
                .map(pat -> API_PREFIX + pat)
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private boolean isPublicCommonEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return Arrays.stream(publicCommonEndpoint)
                .anyMatch(path::contains);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Unauthenticated")
                .build();

        String body;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }
}
