package com.example.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.model.User;

@FeignClient(name = "user-service", url = "http://localhost:8082")
public interface UserFeignClient {
    @GetMapping("/users/{userId}")
    User getUserById(@PathVariable("userId") int userId);
}

