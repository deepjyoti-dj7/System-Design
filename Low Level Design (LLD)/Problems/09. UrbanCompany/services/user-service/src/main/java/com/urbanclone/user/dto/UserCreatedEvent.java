package com.urbanclone.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    
    private Long userId;
    
    private String email;
    
    private String phoneNumber;
    
    private String firstName;
    
    private String lastName;
    
    private String role;
    
    private LocalDateTime createdAt;
}
