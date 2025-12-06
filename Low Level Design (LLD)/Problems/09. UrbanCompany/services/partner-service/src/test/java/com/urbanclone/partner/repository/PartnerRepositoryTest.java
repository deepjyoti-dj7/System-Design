package com.urbanclone.partner.repository;

import com.urbanclone.partner.entity.Partner;
import com.urbanclone.partner.entity.PartnerStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:testdb"
})
@DisplayName("Partner Repository Tests")
class PartnerRepositoryTest {

    @Autowired
    private PartnerRepository partnerRepository;

    private Partner testPartner;

    @BeforeEach
    void setUp() {
        testPartner = Partner.builder()
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
    }

    @Test
    @DisplayName("Should save partner successfully")
    void shouldSavePartner() {
        // When
        Partner saved = partnerRepository.save(testPartner);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getBusinessName()).isEqualTo(testPartner.getBusinessName());
    }

    @Test
    @DisplayName("Should find partner by id")
    void shouldFindPartnerById() {
        // Given
        Partner saved = partnerRepository.save(testPartner);

        // When
        Optional<Partner> found = partnerRepository.findById(saved.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getBusinessName()).isEqualTo(testPartner.getBusinessName());
    }

    @Test
    @DisplayName("Should find partner by email")
    void shouldFindPartnerByEmail() {
        // Given
        partnerRepository.save(testPartner);

        // When
        Optional<Partner> found = partnerRepository.findByEmail("partner@example.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("partner@example.com");
    }

    @Test
    @DisplayName("Should check if email exists")
    void shouldCheckIfEmailExists() {
        // Given
        partnerRepository.save(testPartner);

        // When
        boolean exists = partnerRepository.existsByEmail("partner@example.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should check if phone number exists")
    void shouldCheckIfPhoneNumberExists() {
        // Given
        partnerRepository.save(testPartner);

        // When
        boolean exists = partnerRepository.existsByPhoneNumber("+1234567890");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should find partners by status")
    void shouldFindPartnersByStatus() {
        // Given
        partnerRepository.save(testPartner);
        
        Partner inactivePartner = Partner.builder()
                .userId(101L)
                .businessName("Inactive Provider")
                .email("inactive@example.com")
                .phoneNumber("+9876543210")
                .status(PartnerStatus.INACTIVE)
                .latitude(40.7128)
                .longitude(-74.0060)
                .rating(3.5)
                .createdAt(LocalDateTime.now())
                .build();
        partnerRepository.save(inactivePartner);

        // When
        List<Partner> activePartners = partnerRepository.findByStatus(PartnerStatus.ACTIVE);

        // Then
        assertThat(activePartners).hasSize(1);
        assertThat(activePartners.get(0).getStatus()).isEqualTo(PartnerStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should find partner by user id")
    void shouldFindPartnerByUserId() {
        // Given
        partnerRepository.save(testPartner);

        // When
        Optional<Partner> found = partnerRepository.findByUserId(100L);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUserId()).isEqualTo(100L);
    }

    @Test
    @DisplayName("Should update partner")
    void shouldUpdatePartner() {
        // Given
        Partner saved = partnerRepository.save(testPartner);

        // When
        saved.setBusinessName("Updated Business Name");
        saved.setRating(5.0);
        Partner updated = partnerRepository.save(saved);

        // Then
        assertThat(updated.getBusinessName()).isEqualTo("Updated Business Name");
        assertThat(updated.getRating()).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Should delete partner")
    void shouldDeletePartner() {
        // Given
        Partner saved = partnerRepository.save(testPartner);
        Long id = saved.getId();

        // When
        partnerRepository.deleteById(id);

        // Then
        Optional<Partner> found = partnerRepository.findById(id);
        assertThat(found).isNotPresent();
    }

    @Test
    @DisplayName("Should find partners with high rating")
    void shouldFindPartnersWithHighRating() {
        // Given
        partnerRepository.save(testPartner);
        
        Partner lowRatedPartner = Partner.builder()
                .userId(102L)
                .businessName("Low Rated Provider")
                .email("lowrated@example.com")
                .phoneNumber("+1111111111")
                .status(PartnerStatus.ACTIVE)
                .latitude(40.7128)
                .longitude(-74.0060)
                .rating(2.5)
                .createdAt(LocalDateTime.now())
                .build();
        partnerRepository.save(lowRatedPartner);

        // When
        List<Partner> highRatedPartners = partnerRepository.findByRatingGreaterThanEqual(4.0);

        // Then
        assertThat(highRatedPartners).hasSize(1);
        assertThat(highRatedPartners.get(0).getRating()).isGreaterThanOrEqualTo(4.0);
    }
}
