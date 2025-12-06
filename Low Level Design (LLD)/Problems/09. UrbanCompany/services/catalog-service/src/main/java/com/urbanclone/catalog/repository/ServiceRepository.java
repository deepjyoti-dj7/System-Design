package com.urbanclone.catalog.repository;

import com.urbanclone.catalog.entity.Service;
import com.urbanclone.catalog.entity.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    
    List<Service> findByStatus(ServiceStatus status);
    
    List<Service> findByCategoryId(Long categoryId);
    
    List<Service> findByIsPopularTrue();
}
