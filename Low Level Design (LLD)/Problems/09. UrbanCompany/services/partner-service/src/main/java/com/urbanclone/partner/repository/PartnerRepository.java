package com.urbanclone.partner.repository;

import com.urbanclone.partner.entity.Partner;
import com.urbanclone.partner.entity.PartnerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    
    Optional<Partner> findByUserId(Long userId);
    
    List<Partner> findByStatus(PartnerStatus status);
    
    @Query("SELECT p FROM Partner p WHERE p.status = :status AND p.isAvailable = true " +
           "AND :serviceId MEMBER OF p.serviceIds")
    List<Partner> findAvailablePartnersByService(
            @Param("status") PartnerStatus status,
            @Param("serviceId") Long serviceId
    );
    
    @Query(value = "SELECT * FROM partners p WHERE p.status = 'ACTIVE' " +
           "AND p.is_available = true " +
           "AND ST_Distance_Sphere(point(p.longitude, p.latitude), point(:longitude, :latitude)) <= (p.radius_km * 1000)",
           nativeQuery = true)
    List<Partner> findNearbyPartners(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );
}
