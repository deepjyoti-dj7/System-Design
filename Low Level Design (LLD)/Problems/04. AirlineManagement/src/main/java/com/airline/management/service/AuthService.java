package com.airline.management.service;

import com.airline.management.dto.user.LoginRequest;
import com.airline.management.dto.user.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
