package com.urbanclone.catalog.service;

import com.urbanclone.catalog.dto.ServiceDto;
import com.urbanclone.catalog.entity.Service;
import com.urbanclone.catalog.entity.ServiceStatus;
import com.urbanclone.catalog.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class CatalogService {
    
    private final ServiceRepository serviceRepository;
    
    @Transactional(readOnly = true)
    public List<ServiceDto> getAllActiveServices() {
        return serviceRepository.findByStatus(ServiceStatus.ACTIVE)
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public ServiceDto getService(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return mapToDto(service);
    }
    
    @Transactional(readOnly = true)
    public List<ServiceDto> getServicesByCategory(Long categoryId) {
        return serviceRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ServiceDto> getPopularServices() {
        return serviceRepository.findByIsPopularTrue()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    
    private ServiceDto mapToDto(Service service) {
        return ServiceDto.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .categoryId(service.getCategory().getId())
                .basePrice(service.getBasePrice())
                .durationMinutes(service.getDurationMinutes())
                .imageUrl(service.getImageUrl())
                .features(service.getFeatures())
                .averageRating(service.getAverageRating())
                .isPopular(service.getIsPopular())
                .build();
    }
}
