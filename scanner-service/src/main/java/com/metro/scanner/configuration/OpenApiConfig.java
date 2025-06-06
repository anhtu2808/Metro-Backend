package com.metro.scanner.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Scanner Service API", version = "v1"),
        servers = {
                @Server(url = "/api/v1/scanners", description = "Via API Gateway"),
                @Server(url = "/", description = "Direct"),
        }
)
@Configuration
public class OpenApiConfig {
}
