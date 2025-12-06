package com.urbanclone.partner.service;

import com.urbanclone.partner.dto.PartnerDto;
import com.urbanclone.partner.dto.PartnerRegistrationRequest;
import com.urbanclone.partner.entity.Partner;
import com.urbanclone.partner.entity.PartnerStatus;
import com.urbanclone.partner.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerService {
    
    private final PartnerRepository partnerRepository;
    
    @Transactional
    public PartnerDto registerPartner(PartnerRegistrationRequest request) {
        log.info("Registering new partner: {}", request.getBusinessName());
        
        Partner partner = Partner.builder()
                .userId(request.getUserId())
                .businessName(request.getBusinessName())
                .status(PartnerStatus.PENDING_VERIFICATION)
                .serviceIds(request.getServiceIds())
                .bio(request.getBio())
                .yearsOfExperience(request.getYearsOfExperience())
                .isAvailable(false)
                .build();
        
        partner = partnerRepository.save(partner);
        log.info("Partner registered with ID: {}", partner.getId());
        
        return mapToDto(partner);
    }
    
    @Transactional(readOnly = true)
    public PartnerDto getPartner(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        return mapToDto(partner);
    }
    
    @Transactional(readOnly = true)
    public List<PartnerDto> getAvailablePartners(Long serviceId, Double latitude, Double longitude) {
        // Find nearby partners who offer this service
        List<Partner> partners = partnerRepository.findAvailablePartnersByService(
                PartnerStatus.ACTIVE, serviceId);
        
        return partners.stream()
                .map(this::mapToDto)
                .toList();
    }
    
    private PartnerDto mapToDto(Partner partner) {
        return PartnerDto.builder()
                .id(partner.getId())
                .userId(partner.getUserId())
                .businessName(partner.getBusinessName())
                .status(partner.getStatus())
                .averageRating(partner.getAverageRating())
                .totalCompletedBookings(partner.getTotalCompletedBookings())
                .bio(partner.getBio())
                .yearsOfExperience(partner.getYearsOfExperience())
                .isAvailable(partner.getIsAvailable())
                .build();
    }
}
