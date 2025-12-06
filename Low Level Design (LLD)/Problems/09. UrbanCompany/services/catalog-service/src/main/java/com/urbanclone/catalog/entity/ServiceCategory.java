package com.urbanclone.catalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    private String iconUrl;
    
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private ServiceCategory parentCategory;
    
    private Integer displayOrder;
    
    private Boolean isActive = true;
}
