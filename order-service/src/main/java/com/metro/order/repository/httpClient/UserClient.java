package com.metro.order.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.order.configuration.FeignClientConfig;
import com.metro.order.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${app.services.user.url}", configuration = FeignClientConfig.class)
public interface UserClient {
     @GetMapping("/users/{id}")
     ApiResponse<UserResponse> getUser(@PathVariable("id") Long id);
     @GetMapping("/users/my-info")
     ApiResponse<UserResponse> getMyInfo();
}
