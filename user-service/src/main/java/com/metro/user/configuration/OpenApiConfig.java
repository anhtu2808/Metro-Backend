package com.metro.user.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(title = "User Service API", version = "v1"),
        servers = {
            @Server(url = "/api/v1/", description = "Via API Gateway"),
            @Server(url = "/api/v1/user-service", description = "For health check"),
            @Server(url = "/", description = "Direct"),
        })
@Configuration
public class OpenApiConfig {}
