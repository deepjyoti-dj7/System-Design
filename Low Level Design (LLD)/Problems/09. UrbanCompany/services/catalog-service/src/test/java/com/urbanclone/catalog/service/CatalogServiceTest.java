package com.urbanclone.catalog.service;

import com.urbanclone.catalog.dto.ServiceDto;
import com.urbanclone.catalog.entity.Service;
import com.urbanclone.catalog.entity.ServiceCategory;
import com.urbanclone.catalog.entity.ServiceStatus;
import com.urbanclone.catalog.exception.ServiceNotFoundException;
import com.urbanclone.catalog.repository.ServiceRepository;
import com.urbanclone.catalog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Catalog Service Tests")
class CatalogServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CatalogService catalogService;

    private Service testService;
    private ServiceCategory testCategory;

    @BeforeEach
    void setUp() {
        testCategory = ServiceCategory.builder()
                .id(1L)
                .name("Home Cleaning")
                .description("Professional home cleaning services")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        testService = Service.builder()
                .id(1L)
                .name("Deep Cleaning")
                .description("Complete deep cleaning service")
                .category(testCategory)
                .basePrice(BigDecimal.valueOf(100.00))
                .currency("USD")
                .durationMinutes(120)
                .status(ServiceStatus.ACTIVE)
                .rating(4.5)
                .totalBookings(150)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create service successfully")
    void shouldCreateService() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(serviceRepository.save(any(Service.class))).thenReturn(testService);

        // When
        ServiceDto result = catalogService.createService(testService);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(testService.getName());
        assertThat(result.getBasePrice()).isEqualTo(testService.getBasePrice());

        verify(serviceRepository).save(any(Service.class));
    }

    @Test
    @DisplayName("Should get service by id successfully")
    void shouldGetServiceById() {
        // Given
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(testService));

        // When
        ServiceDto result = catalogService.getServiceById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testService.getId());
        assertThat(result.getName()).isEqualTo(testService.getName());
        verify(serviceRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when service not found")
    void shouldThrowExceptionWhenServiceNotFound() {
        // Given
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> catalogService.getServiceById(1L))
                .isInstanceOf(ServiceNotFoundException.class)
                .hasMessageContaining("Service not found with id: 1");
    }

    @Test
    @DisplayName("Should get services by category")
    void shouldGetServicesByCategory() {
        // Given
        List<Service> services = Arrays.asList(testService);
        when(serviceRepository.findByCategoryId(1L)).thenReturn(services);

        // When
        List<ServiceDto> result = catalogService.getServicesByCategory(1L);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryId()).isEqualTo(1L);
        verify(serviceRepository).findByCategoryId(1L);
    }

    @Test
    @DisplayName("Should get active services")
    void shouldGetActiveServices() {
        // Given
        List<Service> services = Arrays.asList(testService);
        when(serviceRepository.findByStatus(ServiceStatus.ACTIVE)).thenReturn(services);

        // When
        List<ServiceDto> result = catalogService.getActiveServices();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(ServiceStatus.ACTIVE);
        verify(serviceRepository).findByStatus(ServiceStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should search services by name")
    void shouldSearchServicesByName() {
        // Given
        List<Service> services = Arrays.asList(testService);
        when(serviceRepository.searchByName("cleaning")).thenReturn(services);

        // When
        List<ServiceDto> result = catalogService.searchServices("cleaning");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).containsIgnoringCase("cleaning");
        verify(serviceRepository).searchByName("cleaning");
    }

    @Test
    @DisplayName("Should update service successfully")
    void shouldUpdateService() {
        // Given
        ServiceDto updateDto = ServiceDto.builder()
                .id(1L)
                .name("Premium Deep Cleaning")
                .basePrice(BigDecimal.valueOf(150.00))
                .build();

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(testService));
        when(serviceRepository.save(any(Service.class))).thenReturn(testService);

        // When
        ServiceDto result = catalogService.updateService(1L, updateDto);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceRepository).save(serviceCaptor.capture());
        Service updatedService = serviceCaptor.getValue();
        assertThat(updatedService.getName()).isEqualTo("Premium Deep Cleaning");
        assertThat(updatedService.getBasePrice()).isEqualTo(BigDecimal.valueOf(150.00));
    }

    @Test
    @DisplayName("Should deactivate service")
    void shouldDeactivateService() {
        // Given
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(testService));
        when(serviceRepository.save(any(Service.class))).thenReturn(testService);

        // When
        catalogService.deactivateService(1L);

        // Then
        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceRepository).save(serviceCaptor.capture());
        Service deactivatedService = serviceCaptor.getValue();
        assertThat(deactivatedService.getStatus()).isEqualTo(ServiceStatus.INACTIVE);
    }

    @Test
    @DisplayName("Should update service rating")
    void shouldUpdateServiceRating() {
        // Given
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(testService));
        when(serviceRepository.save(any(Service.class))).thenReturn(testService);

        // When
        catalogService.updateRating(1L, 5.0);

        // Then
        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceRepository).save(serviceCaptor.capture());
        Service updatedService = serviceCaptor.getValue();
        assertThat(updatedService.getRating()).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Should increment total bookings")
    void shouldIncrementTotalBookings() {
        // Given
        int initialBookings = testService.getTotalBookings();
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(testService));
        when(serviceRepository.save(any(Service.class))).thenReturn(testService);

        // When
        catalogService.incrementBookings(1L);

        // Then
        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceRepository).save(serviceCaptor.capture());
        Service updatedService = serviceCaptor.getValue();
        assertThat(updatedService.getTotalBookings()).isEqualTo(initialBookings + 1);
    }

    @Test
    @DisplayName("Should get popular services")
    void shouldGetPopularServices() {
        // Given
        List<Service> services = Arrays.asList(testService);
        when(serviceRepository.findTopByRating(10)).thenReturn(services);

        // When
        List<ServiceDto> result = catalogService.getPopularServices(10);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRating()).isGreaterThan(0);
        verify(serviceRepository).findTopByRating(10);
    }
}
