package com.metro.payment.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Payment Service API", version = "v1"),
        servers = {
                @Server(url = "/api/v1/", description = "Via API Gateway"),
                @Server(url = "/api/v1/payment-service", description = "For health check"),
                @Server(url = "/", description = "Direct"),
        }
)
@Configuration
public class OpenApiConfig {
}
