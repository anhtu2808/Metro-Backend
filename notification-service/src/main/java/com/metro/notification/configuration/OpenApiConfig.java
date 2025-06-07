package com.metro.notification.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Notification Service API", version = "v1"),
        servers = {
                @Server(url = "/api/v1/", description = "Via API Gateway"),
                @Server(url = "/api/v1/notification-service", description = "For health check"),
                @Server(url = "/", description = "Direct"),
        }
)
@Configuration
public class OpenApiConfig {
}
