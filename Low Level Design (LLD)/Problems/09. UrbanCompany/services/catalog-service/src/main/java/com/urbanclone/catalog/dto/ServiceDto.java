package com.urbanclone.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    
    private Long id;
    
    private String name;
    
    private String description;
    
    private Long categoryId;
    
    private BigDecimal basePrice;
    
    private Integer durationMinutes;
    
    private String imageUrl;
    
    private List<String> features;
    
    private Double averageRating;
    
    private Boolean isPopular;
}
