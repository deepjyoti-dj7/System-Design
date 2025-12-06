package com.urbanclone.catalog.repository;

import com.urbanclone.catalog.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<ServiceCategory, Long> {
    
    List<ServiceCategory> findByIsActiveTrue();
    
    List<ServiceCategory> findByParentCategoryIsNull();
}
