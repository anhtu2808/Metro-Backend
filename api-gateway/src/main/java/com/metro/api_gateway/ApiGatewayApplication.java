package com.metro.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
		System.out.println("Swagger UI available at: http://localhost:8888/webjars/swagger-ui/index.html");
	}

}
