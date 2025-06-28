package com.metro.payment.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.payment.configuration.FeignClientConfig;
import com.metro.payment.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${USER_SERVICE_HOST:http://localhost:8080}", configuration = FeignClientConfig.class)
public interface UserClient {
     @GetMapping("/users/{id}")
     ApiResponse<UserResponse> getUser(@PathVariable("id") Long id);
     @GetMapping("/users/my-info")
     ApiResponse<UserResponse> getMyInfo();
}
