package com.urbanclone.partner.repository;

import com.urbanclone.partner.entity.PartnerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface PartnerAvailabilityRepository extends JpaRepository<PartnerAvailability, Long> {
    
    List<PartnerAvailability> findByPartnerId(Long partnerId);
    
    List<PartnerAvailability> findByPartnerIdAndDayOfWeek(Long partnerId, DayOfWeek dayOfWeek);
}
