package com.airline.management.service;

import com.airline.management.dto.user.RegisterRequest;
import com.airline.management.dto.user.UpdateUserRequest;
import com.airline.management.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(RegisterRequest request);
    UserResponse getById(Long id);
    UserResponse getByUsername(String username);
    UserResponse getCurrentUser(); // based on security context
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    List<UserResponse> getAllUsers();
}
