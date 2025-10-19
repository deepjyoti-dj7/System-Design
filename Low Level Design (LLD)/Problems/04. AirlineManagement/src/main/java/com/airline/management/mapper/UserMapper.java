package com.airline.management.mapper;

import com.airline.management.dto.user.RegisterRequest;
import com.airline.management.dto.user.UserResponse;
import com.airline.management.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public void updateEntityFromDto(User user, RegisterRequest req) {
        if (req.getUsername() != null) user.setUsername(req.getUsername());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getFirstName() != null) user.setFirstName(req.getFirstName());
        if (req.getLastName() != null) user.setLastName(req.getLastName());
        // password handled separately
    }
}

