package com.metro.route.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Route Service API", version = "v1"),
        servers = {
                @Server(url = "/api/v1/routes", description = "Via API Gateway"),
                @Server(url = "/", description = "Direct"),
        }
)
@Configuration
public class OpenApiConfig {
}
