package com.urbanclone.partner.service;

import com.urbanclone.partner.dto.PartnerDto;
import com.urbanclone.partner.dto.PartnerRegistrationRequest;
import com.urbanclone.partner.entity.Partner;
import com.urbanclone.partner.entity.PartnerStatus;
import com.urbanclone.partner.exception.PartnerNotFoundException;
import com.urbanclone.partner.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Partner Service Tests")
class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private PartnerService partnerService;

    private Partner testPartner;
    private PartnerRegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        testPartner = Partner.builder()
                .id(1L)
                .userId(100L)
                .businessName("Test Service Provider")
                .email("partner@example.com")
                .phoneNumber("+1234567890")
                .status(PartnerStatus.ACTIVE)
                .latitude(40.7128)
                .longitude(-74.0060)
                .rating(4.5)
                .totalCompletedJobs(50)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        registrationRequest = PartnerRegistrationRequest.builder()
                .userId(100L)
                .businessName("Test Service Provider")
                .email("partner@example.com")
                .phoneNumber("+1234567890")
                .latitude(40.7128)
                .longitude(-74.0060)
                .serviceIds(Arrays.asList(1L, 2L, 3L))
                .build();
    }

    @Test
    @DisplayName("Should register partner successfully")
    void shouldRegisterPartner() {
        // Given
        when(partnerRepository.existsByEmail(anyString())).thenReturn(false);
        when(partnerRepository.existsByPhoneNumber(anyString())).thenReturn(false);
        when(partnerRepository.save(any(Partner.class))).thenReturn(testPartner);

        // When
        PartnerDto result = partnerService.registerPartner(registrationRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBusinessName()).isEqualTo(registrationRequest.getBusinessName());
        assertThat(result.getEmail()).isEqualTo(registrationRequest.getEmail());

        ArgumentCaptor<Partner> partnerCaptor = ArgumentCaptor.forClass(Partner.class);
        verify(partnerRepository).save(partnerCaptor.capture());
        Partner savedPartner = partnerCaptor.getValue();
        assertThat(savedPartner.getStatus()).isEqualTo(PartnerStatus.PENDING_VERIFICATION);
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailExists() {
        // Given
        when(partnerRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> partnerService.registerPartner(registrationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email already registered");

        verify(partnerRepository, never()).save(any(Partner.class));
    }

    @Test
    @DisplayName("Should get partner by id successfully")
    void shouldGetPartnerById() {
        // Given
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(testPartner));

        // When
        PartnerDto result = partnerService.getPartnerById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testPartner.getId());
        assertThat(result.getBusinessName()).isEqualTo(testPartner.getBusinessName());
        verify(partnerRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when partner not found")
    void shouldThrowExceptionWhenPartnerNotFound() {
        // Given
        when(partnerRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> partnerService.getPartnerById(1L))
                .isInstanceOf(PartnerNotFoundException.class)
                .hasMessageContaining("Partner not found with id: 1");
    }

    @Test
    @DisplayName("Should find nearby partners")
    void shouldFindNearbyPartners() {
        // Given
        double latitude = 40.7128;
        double longitude = -74.0060;
        double radiusKm = 10.0;
        List<Partner> partners = Arrays.asList(testPartner);
        
        when(partnerRepository.findNearbyPartners(
                anyDouble(), anyDouble(), anyDouble())).thenReturn(partners);

        // When
        List<PartnerDto> result = partnerService.findNearbyPartners(latitude, longitude, radiusKm);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(testPartner.getId());
        verify(partnerRepository).findNearbyPartners(latitude, longitude, radiusKm);
    }

    @Test
    @DisplayName("Should find available partners for service")
    void shouldFindAvailablePartnersForService() {
        // Given
        Long serviceId = 200L;
        double latitude = 40.7128;
        double longitude = -74.0060;
        List<Partner> partners = Arrays.asList(testPartner);
        
        when(partnerRepository.findAvailablePartnersForService(
                anyLong(), anyDouble(), anyDouble(), anyDouble())).thenReturn(partners);

        // When
        List<PartnerDto> result = partnerService.findAvailablePartnersForService(
                serviceId, latitude, longitude, 10.0);

        // Then
        assertThat(result).hasSize(1);
        verify(partnerRepository).findAvailablePartnersForService(
                eq(serviceId), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    @DisplayName("Should verify partner successfully")
    void shouldVerifyPartner() {
        // Given
        testPartner.setStatus(PartnerStatus.PENDING_VERIFICATION);
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(testPartner));
        when(partnerRepository.save(any(Partner.class))).thenReturn(testPartner);

        // When
        PartnerDto result = partnerService.verifyPartner(1L);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Partner> partnerCaptor = ArgumentCaptor.forClass(Partner.class);
        verify(partnerRepository).save(partnerCaptor.capture());
        Partner verifiedPartner = partnerCaptor.getValue();
        assertThat(verifiedPartner.getStatus()).isEqualTo(PartnerStatus.ACTIVE);
        assertThat(verifiedPartner.getVerifiedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should update partner rating")
    void shouldUpdatePartnerRating() {
        // Given
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(testPartner));
        when(partnerRepository.save(any(Partner.class))).thenReturn(testPartner);

        // When
        PartnerDto result = partnerService.updateRating(1L, 5.0);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Partner> partnerCaptor = ArgumentCaptor.forClass(Partner.class);
        verify(partnerRepository).save(partnerCaptor.capture());
        Partner updatedPartner = partnerCaptor.getValue();
        assertThat(updatedPartner.getRating()).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Should deactivate partner")
    void shouldDeactivatePartner() {
        // Given
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(testPartner));
        when(partnerRepository.save(any(Partner.class))).thenReturn(testPartner);

        // When
        partnerService.deactivatePartner(1L);

        // Then
        ArgumentCaptor<Partner> partnerCaptor = ArgumentCaptor.forClass(Partner.class);
        verify(partnerRepository).save(partnerCaptor.capture());
        Partner deactivatedPartner = partnerCaptor.getValue();
        assertThat(deactivatedPartner.getStatus()).isEqualTo(PartnerStatus.INACTIVE);
    }

    @Test
    @DisplayName("Should increment completed jobs")
    void shouldIncrementCompletedJobs() {
        // Given
        int initialJobs = testPartner.getTotalCompletedJobs();
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(testPartner));
        when(partnerRepository.save(any(Partner.class))).thenReturn(testPartner);

        // When
        partnerService.incrementCompletedJobs(1L);

        // Then
        ArgumentCaptor<Partner> partnerCaptor = ArgumentCaptor.forClass(Partner.class);
        verify(partnerRepository).save(partnerCaptor.capture());
        Partner updatedPartner = partnerCaptor.getValue();
        assertThat(updatedPartner.getTotalCompletedJobs()).isEqualTo(initialJobs + 1);
    }

    @Test
    @DisplayName("Should get partners by status")
    void shouldGetPartnersByStatus() {
        // Given
        List<Partner> partners = Arrays.asList(testPartner);
        when(partnerRepository.findByStatus(PartnerStatus.ACTIVE)).thenReturn(partners);

        // When
        List<PartnerDto> result = partnerService.getPartnersByStatus(PartnerStatus.ACTIVE);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(PartnerStatus.ACTIVE);
        verify(partnerRepository).findByStatus(PartnerStatus.ACTIVE);
    }
}
