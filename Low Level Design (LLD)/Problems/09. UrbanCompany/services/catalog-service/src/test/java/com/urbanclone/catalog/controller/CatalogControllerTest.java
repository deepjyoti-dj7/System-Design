package com.urbanclone.catalog.controller;

import com.urbanclone.catalog.dto.ServiceDto;
import com.urbanclone.catalog.entity.ServiceStatus;
import com.urbanclone.catalog.service.CatalogService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatalogController.class)
@DisplayName("Catalog Controller Tests")
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CatalogService catalogService;

    private ServiceDto testServiceDto;

    @BeforeEach
    void setUp() {
        testServiceDto = ServiceDto.builder()
                .id(1L)
                .name("Deep Cleaning")
                .description("Complete deep cleaning service")
                .categoryId(1L)
                .basePrice(BigDecimal.valueOf(100.00))
                .currency("USD")
                .durationMinutes(120)
                .status(ServiceStatus.ACTIVE)
                .rating(4.5)
                .totalBookings(150)
                .build();
    }

    @Test
    @DisplayName("Should create service successfully")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateService() throws Exception {
        // Given
        when(catalogService.createService(any())).thenReturn(testServiceDto);

        // When & Then
        mockMvc.perform(post("/api/v1/services")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testServiceDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Deep Cleaning"));
    }

    @Test
    @DisplayName("Should get service by id")
    @WithMockUser
    void shouldGetServiceById() throws Exception {
        // Given
        when(catalogService.getServiceById(1L)).thenReturn(testServiceDto);

        // When & Then
        mockMvc.perform(get("/api/v1/services/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Deep Cleaning"));
    }

    @Test
    @DisplayName("Should get services by category")
    @WithMockUser
    void shouldGetServicesByCategory() throws Exception {
        // Given
        List<ServiceDto> services = Arrays.asList(testServiceDto);
        when(catalogService.getServicesByCategory(1L)).thenReturn(services);

        // When & Then
        mockMvc.perform(get("/api/v1/services/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].categoryId").value(1L));
    }

    @Test
    @DisplayName("Should get active services")
    @WithMockUser
    void shouldGetActiveServices() throws Exception {
        // Given
        List<ServiceDto> services = Arrays.asList(testServiceDto);
        when(catalogService.getActiveServices()).thenReturn(services);

        // When & Then
        mockMvc.perform(get("/api/v1/services/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should search services")
    @WithMockUser
    void shouldSearchServices() throws Exception {
        // Given
        List<ServiceDto> services = Arrays.asList(testServiceDto);
        when(catalogService.searchServices(anyString())).thenReturn(services);

        // When & Then
        mockMvc.perform(get("/api/v1/services/search")
                        .param("query", "cleaning"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Deep Cleaning"));
    }

    @Test
    @DisplayName("Should update service")
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateService() throws Exception {
        // Given
        when(catalogService.updateService(eq(1L), any(ServiceDto.class)))
                .thenReturn(testServiceDto);

        // When & Then
        mockMvc.perform(put("/api/v1/services/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testServiceDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Should deactivate service")
    @WithMockUser(roles = "ADMIN")
    void shouldDeactivateService() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/services/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should get popular services")
    @WithMockUser
    void shouldGetPopularServices() throws Exception {
        // Given
        List<ServiceDto> services = Arrays.asList(testServiceDto);
        when(catalogService.getPopularServices(10)).thenReturn(services);

        // When & Then
        mockMvc.perform(get("/api/v1/services/popular")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(4.5));
    }
}
