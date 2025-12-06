package com.urbanclone.user.dto;

import com.urbanclone.user.entity.UserRole;
import com.urbanclone.user.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private Long id;
    
    private String email;
    
    private String phoneNumber;
    
    private String firstName;
    
    private String lastName;
    
    private UserRole role;
    
    private UserStatus status;
    
    private AddressDto address;
    
    private String profileImageUrl;
    
    private Boolean emailVerified;
    
    private Boolean phoneVerified;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastLoginAt;
}
