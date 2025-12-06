package com.urbanclone.partner.controller;

import com.urbanclone.partner.dto.PartnerDto;
import com.urbanclone.partner.dto.PartnerRegistrationRequest;
import com.urbanclone.partner.entity.PartnerStatus;
import com.urbanclone.partner.service.PartnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartnerController.class)
@DisplayName("Partner Controller Tests")
class PartnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartnerService partnerService;

    private PartnerDto testPartnerDto;
    private PartnerRegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        testPartnerDto = PartnerDto.builder()
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
    @WithMockUser
    void shouldRegisterPartner() throws Exception {
        // Given
        when(partnerService.registerPartner(any(PartnerRegistrationRequest.class)))
                .thenReturn(testPartnerDto);

        // When & Then
        mockMvc.perform(post("/api/v1/partners/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.businessName").value("Test Service Provider"));
    }

    @Test
    @DisplayName("Should get partner by id")
    @WithMockUser
    void shouldGetPartnerById() throws Exception {
        // Given
        when(partnerService.getPartnerById(1L)).thenReturn(testPartnerDto);

        // When & Then
        mockMvc.perform(get("/api/v1/partners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.businessName").value("Test Service Provider"));
    }

    @Test
    @DisplayName("Should find nearby partners")
    @WithMockUser
    void shouldFindNearbyPartners() throws Exception {
        // Given
        List<PartnerDto> partners = Arrays.asList(testPartnerDto);
        when(partnerService.findNearbyPartners(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(partners);

        // When & Then
        mockMvc.perform(get("/api/v1/partners/nearby")
                        .param("latitude", "40.7128")
                        .param("longitude", "-74.0060")
                        .param("radiusKm", "10.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].businessName").value("Test Service Provider"));
    }

    @Test
    @DisplayName("Should find available partners for service")
    @WithMockUser
    void shouldFindAvailablePartnersForService() throws Exception {
        // Given
        List<PartnerDto> partners = Arrays.asList(testPartnerDto);
        when(partnerService.findAvailablePartnersForService(anyLong(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(partners);

        // When & Then
        mockMvc.perform(get("/api/v1/partners/available")
                        .param("serviceId", "1")
                        .param("latitude", "40.7128")
                        .param("longitude", "-74.0060")
                        .param("radiusKm", "10.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("Should verify partner")
    @WithMockUser(roles = "ADMIN")
    void shouldVerifyPartner() throws Exception {
        // Given
        testPartnerDto.setStatus(PartnerStatus.ACTIVE);
        when(partnerService.verifyPartner(1L)).thenReturn(testPartnerDto);

        // When & Then
        mockMvc.perform(put("/api/v1/partners/1/verify")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should deactivate partner")
    @WithMockUser(roles = "ADMIN")
    void shouldDeactivatePartner() throws Exception {
        // When & Then
        mockMvc.perform(put("/api/v1/partners/1/deactivate")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get partners by status")
    @WithMockUser(roles = "ADMIN")
    void shouldGetPartnersByStatus() throws Exception {
        // Given
        List<PartnerDto> partners = Arrays.asList(testPartnerDto);
        when(partnerService.getPartnersByStatus(PartnerStatus.ACTIVE))
                .thenReturn(partners);

        // When & Then
        mockMvc.perform(get("/api/v1/partners/status/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }
}
