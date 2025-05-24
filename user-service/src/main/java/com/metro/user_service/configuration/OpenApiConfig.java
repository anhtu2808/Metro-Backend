package com.metro.user_service.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "User Service API", version = "v1"),
        servers = @Server(url = "/user-service") // phải trùng với path được route qua Gateway
)
@Configuration
public class OpenApiConfig {
}
