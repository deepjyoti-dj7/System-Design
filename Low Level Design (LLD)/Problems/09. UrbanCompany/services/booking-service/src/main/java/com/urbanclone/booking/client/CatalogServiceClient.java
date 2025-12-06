package com.urbanclone.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "catalog-service")
public interface CatalogServiceClient {
    
    @GetMapping("/api/v1/catalog/services/{serviceId}")
    ServiceDto getServiceById(@PathVariable Long serviceId);
    
    class ServiceDto {
        private Long id;
        private String name;
        private String description;
        private BigDecimal basePrice;
        // getters and setters
    }
}
