package com.airline.management.service.impl;

import com.airline.management.dto.user.LoginRequest;
import com.airline.management.dto.user.LoginResponse;
import com.airline.management.entity.User;
import com.airline.management.repository.UserRepository;
import com.airline.management.security.JwtUtil;
import com.airline.management.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements com.airline.management.service.AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            // Authenticate once
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Extract UserDetails from authentication
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            return new LoginResponse(token);

        } catch (Exception ex) {
            throw new ValidationException("Invalid username or password");
        }
    }
}
