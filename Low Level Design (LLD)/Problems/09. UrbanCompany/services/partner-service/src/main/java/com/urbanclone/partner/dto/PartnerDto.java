package com.urbanclone.partner.dto;

import com.urbanclone.partner.entity.PartnerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {
    
    private Long id;
    
    private Long userId;
    
    private String businessName;
    
    private PartnerStatus status;
    
    private Double averageRating;
    
    private Integer totalCompletedBookings;
    
    private String bio;
    
    private Integer yearsOfExperience;
    
    private Boolean isAvailable;
}
