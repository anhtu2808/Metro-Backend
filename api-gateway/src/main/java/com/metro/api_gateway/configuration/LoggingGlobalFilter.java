package com.metro.api_gateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. Log request gốc
        String method = exchange.getRequest().getMethod() != null
                ? exchange.getRequest().getMethod().name()
                : "UNKNOWN";
        String originalPath = exchange.getRequest().getURI().getRawPath();
        log.info("[GATEWAY] Incoming → {} {}", method, originalPath);

        // 2. Thực hiện chain và sau đó log downstream URI
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Lấy URI downstream đã được set bởi RouteToRequestUrlFilter
            URI downstreamUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

            if (downstreamUri != null) {
                log.info("[GATEWAY] Forwarded → {} {}", method, downstreamUri.toASCIIString());
            } else {
                log.info("[GATEWAY] No downstream URI found (route chưa khớp hoặc filter order chưa phù hợp)");
            }
        }));
    }

    @Override
    public int getOrder() {
        // Đặt order sao cho callback .then(...) được chạy sau khi RouteToRequestUrlFilter thực thi
        return Ordered.LOWEST_PRECEDENCE;
    }
}
