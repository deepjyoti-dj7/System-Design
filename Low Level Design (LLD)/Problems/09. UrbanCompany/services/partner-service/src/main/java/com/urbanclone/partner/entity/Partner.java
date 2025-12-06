package com.urbanclone.partner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "partners")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Partner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private String businessName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartnerStatus status;
    
    @ElementCollection
    @CollectionTable(name = "partner_skills", joinColumns = @JoinColumn(name = "partner_id"))
    @Column(name = "service_id")
    private Set<Long> serviceIds;
    
    @Embedded
    private PartnerVerification verification;
    
    private Double averageRating;
    
    private Integer totalCompletedBookings = 0;
    
    private String profileImageUrl;
    
    @Column(length = 1000)
    private String bio;
    
    private Integer yearsOfExperience;
    
    @ElementCollection
    @CollectionTable(name = "partner_certifications", joinColumns = @JoinColumn(name = "partner_id"))
    private Set<String> certifications;
    
    @Embedded
    private WorkingArea workingArea;
    
    private Boolean isAvailable = true;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @Version
    private Long version;
}
