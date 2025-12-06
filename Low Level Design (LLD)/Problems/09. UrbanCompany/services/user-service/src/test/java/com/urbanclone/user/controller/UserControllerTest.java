package com.urbanclone.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanclone.user.dto.UserDto;
import com.urbanclone.user.dto.UserRegistrationRequest;
import com.urbanclone.user.entity.UserRole;
import com.urbanclone.user.entity.UserStatus;
import com.urbanclone.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("User Controller Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDto testUserDto;
    private UserRegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        testUserDto = UserDto.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("+1234567890")
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .emailVerified(false)
                .phoneVerified(false)
                .createdAt(LocalDateTime.now())
                .build();

        registrationRequest = UserRegistrationRequest.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("+1234567890")
                .password("password123")
                .role(UserRole.CUSTOMER)
                .build();
    }

    @Test
    @DisplayName("Should register user successfully")
    void shouldRegisterUser() throws Exception {
        // Given
        when(userService.registerUser(any(UserRegistrationRequest.class)))
                .thenReturn(testUserDto);

        // When & Then
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @DisplayName("Should return bad request when email is invalid")
    void shouldReturnBadRequestWhenEmailInvalid() throws Exception {
        // Given
        registrationRequest.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get user by id successfully")
    void shouldGetUserById() throws Exception {
        // Given
        when(userService.getUserById(1L)).thenReturn(testUserDto);

        // When & Then
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @DisplayName("Should get user by email successfully")
    void shouldGetUserByEmail() throws Exception {
        // Given
        when(userService.getUserByEmail("test@example.com")).thenReturn(testUserDto);

        // When & Then
        mockMvc.perform(get("/api/v1/users/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUser() throws Exception {
        // Given
        UserDto updateDto = UserDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .build();

        when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(testUserDto);

        // When & Then
        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Should verify email successfully")
    void shouldVerifyEmail() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/users/1/verify-email"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should deactivate user successfully")
    void shouldDeactivateUser() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());
    }
}
