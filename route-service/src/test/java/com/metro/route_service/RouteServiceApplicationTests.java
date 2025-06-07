package com.metro.route_service;

import com.metro.route.RouteServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = RouteServiceApplication.class)
@ActiveProfiles("test")
class RouteServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
