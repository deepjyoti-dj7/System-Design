package com.urbanclone.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    
    @GetMapping("/api/v1/users/{userId}")
    UserDto getUserById(@PathVariable Long userId);
    
    class UserDto {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        // getters and setters
    }
}
