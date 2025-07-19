package com.metro.order.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
// Bypass cho callback từ ticket-service đến order-service change status của ticket order bằng internal secret
public class InternalSecretAuthFilter extends OncePerRequestFilter {

    @Value("${internal.secret}")
    private String internalSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getRequestURI();
        String secret = request.getHeader("X-INTERNAL-SECRET");

        log.info("🔐 Path: {}, Header secret: {}", path, secret);

        if (path.matches("^/ticket-orders/\\d+/status$")|| path.matches("^/ticket-orders/\\d+/status-purchase$") && internalSecret.equals(secret)) {
            log.info("✅ Internal secret matched. Bypassing JWT and authenticating manually.");

            // Tạo một Authentication object giả để bypass security
            var auth = new PreAuthenticatedAuthenticationToken("internal", null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}