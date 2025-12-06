package com.urbanclone.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    
    private String code;
    
    private String message;
    
    private LocalDateTime timestamp;
    
    private String path;
}
