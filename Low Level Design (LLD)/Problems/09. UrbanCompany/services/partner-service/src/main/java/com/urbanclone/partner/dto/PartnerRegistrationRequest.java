package com.urbanclone.partner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRegistrationRequest {
    
    @NotNull
    private Long userId;
    
    @NotBlank
    private String businessName;
    
    @NotNull
    private Set<Long> serviceIds;
    
    private String bio;
    
    private Integer yearsOfExperience;
}
