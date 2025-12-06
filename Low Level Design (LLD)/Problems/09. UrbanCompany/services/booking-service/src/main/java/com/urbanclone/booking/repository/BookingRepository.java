package com.urbanclone.booking.repository;

import com.urbanclone.booking.entity.Booking;
import com.urbanclone.booking.entity.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    Optional<Booking> findByBookingNumber(String bookingNumber);
    
    Page<Booking> findByCustomerId(Long customerId, Pageable pageable);
    
    Page<Booking> findByPartnerId(Long partnerId, Pageable pageable);
    
    Page<Booking> findByStatus(BookingStatus status, Pageable pageable);
    
    List<Booking> findByPartnerIdAndStatus(Long partnerId, BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.scheduledAt BETWEEN :start AND :end")
    List<Booking> findBookingsByDateRange(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    
    @Query("SELECT b FROM Booking b WHERE b.customerId = :customerId AND b.status IN :statuses")
    List<Booking> findActiveBookingsByCustomer(
            @Param("customerId") Long customerId,
            @Param("statuses") List<BookingStatus> statuses
    );
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.partnerId = :partnerId AND b.status = :status")
    Long countPartnerActiveBookings(
            @Param("partnerId") Long partnerId,
            @Param("status") BookingStatus status
    );
}
