package com.urbanclone.partner.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerVerification {
    
    private Boolean identityVerified = false;
    
    private Boolean addressVerified = false;
    
    private Boolean backgroundCheckCompleted = false;
    
    private Boolean certificatesVerified = false;
    
    private String governmentIdUrl;
    
    private String addressProofUrl;
    
    private LocalDateTime verifiedAt;
    
    private String verifiedBy;
}
