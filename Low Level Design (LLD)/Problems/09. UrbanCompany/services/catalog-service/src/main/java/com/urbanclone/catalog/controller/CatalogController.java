package com.urbanclone.catalog.controller;

import com.urbanclone.catalog.dto.ServiceDto;
import com.urbanclone.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {
    
    private final CatalogService catalogService;
    
    @GetMapping("/services")
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        List<ServiceDto> services = catalogService.getAllActiveServices();
        return ResponseEntity.ok(services);
    }
    
    @GetMapping("/services/{serviceId}")
    public ResponseEntity<ServiceDto> getService(@PathVariable Long serviceId) {
        ServiceDto service = catalogService.getService(serviceId);
        return ResponseEntity.ok(service);
    }
    
    @GetMapping("/services/category/{categoryId}")
    public ResponseEntity<List<ServiceDto>> getServicesByCategory(@PathVariable Long categoryId) {
        List<ServiceDto> services = catalogService.getServicesByCategory(categoryId);
        return ResponseEntity.ok(services);
    }
    
    @GetMapping("/services/popular")
    public ResponseEntity<List<ServiceDto>> getPopularServices() {
        List<ServiceDto> services = catalogService.getPopularServices();
        return ResponseEntity.ok(services);
    }
}
