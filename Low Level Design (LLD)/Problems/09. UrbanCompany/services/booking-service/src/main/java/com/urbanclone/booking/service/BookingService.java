package com.urbanclone.booking.service;

import com.urbanclone.booking.dto.*;
import com.urbanclone.booking.entity.*;
import com.urbanclone.booking.exception.BookingNotFoundException;
import com.urbanclone.booking.exception.InvalidBookingStateException;
import com.urbanclone.booking.repository.BookingRepository;
import com.urbanclone.booking.statemachine.BookingStateMachine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final BookingStateMachine stateMachine;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PricingService pricingService;
    
    @Transactional
    public BookingDto createBooking(Long customerId, BookingCreateRequest request) {
        log.info("Creating booking for customer: {}, service: {}", customerId, request.getServiceId());
        
        // Calculate pricing
        PricingDetails pricing = pricingService.calculatePrice(request.getServiceId());
        
        // Create booking
        Booking booking = Booking.builder()
                .bookingNumber(generateBookingNumber())
                .customerId(customerId)
                .serviceId(request.getServiceId())
                .scheduledAt(request.getScheduledAt())
                .status(BookingStatus.PENDING)
                .address(mapToBookingAddress(request.getAddress()))
                .basePrice(pricing.getBasePrice())
                .discount(pricing.getDiscount())
                .tax(pricing.getTax())
                .totalPrice(pricing.getTotalPrice())
                .customerNotes(request.getCustomerNotes())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        
        booking = bookingRepository.save(booking);
        log.info("Booking created: {}", booking.getBookingNumber());
        
        // Publish booking created event
        publishBookingCreatedEvent(booking);
        
        return mapToBookingDto(booking);
    }
    
    @Transactional
    public BookingDto assignPartner(Long bookingId, Long partnerId) {
        log.info("Assigning partner {} to booking {}", partnerId, bookingId);
        
        Booking booking = getBookingById(bookingId);
        
        // Validate state transition
        if (!stateMachine.canTransition(booking.getStatus(), BookingStatus.PARTNER_ASSIGNED)) {
            throw new InvalidBookingStateException(
                    "Cannot assign partner in current state: " + booking.getStatus());
        }
        
        booking.setPartnerId(partnerId);
        booking.setStatus(BookingStatus.PARTNER_ASSIGNED);
        booking.setPartnerAssignedAt(LocalDateTime.now());
        
        booking = bookingRepository.save(booking);
        
        // Publish partner assigned event
        publishPartnerAssignedEvent(booking);
        
        return mapToBookingDto(booking);
    }
    
    @Transactional
    public BookingDto updateBookingStatus(Long bookingId, BookingStatus newStatus) {
        log.info("Updating booking {} status to {}", bookingId, newStatus);
        
        Booking booking = getBookingById(bookingId);
        
        if (!stateMachine.canTransition(booking.getStatus(), newStatus)) {
            throw new InvalidBookingStateException(
                    String.format("Cannot transition from %s to %s", 
                            booking.getStatus(), newStatus));
        }
        
        BookingStatus oldStatus = booking.getStatus();
        booking.setStatus(newStatus);
        
        // Update timestamps based on status
        switch (newStatus) {
            case PARTNER_ARRIVED -> booking.setPartnerArrivedAt(LocalDateTime.now());
            case IN_PROGRESS -> booking.setServiceStartedAt(LocalDateTime.now());
            case COMPLETED -> booking.setCompletedAt(LocalDateTime.now());
            case CANCELLED -> booking.setCancelledAt(LocalDateTime.now());
        }
        
        booking = bookingRepository.save(booking);
        
        // Publish status change event
        publishStatusChangeEvent(booking, oldStatus, newStatus);
        
        return mapToBookingDto(booking);
    }
    
    @Transactional
    public BookingDto cancelBooking(Long bookingId, String reason) {
        log.info("Cancelling booking: {}", bookingId);
        
        Booking booking = getBookingById(bookingId);
        
        if (!stateMachine.canTransition(booking.getStatus(), BookingStatus.CANCELLED)) {
            throw new InvalidBookingStateException(
                    "Booking cannot be cancelled in current state: " + booking.getStatus());
        }
        
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);
        booking.setCancelledAt(LocalDateTime.now());
        
        booking = bookingRepository.save(booking);
        
        // Publish cancellation event
        publishBookingCancelledEvent(booking);
        
        return mapToBookingDto(booking);
    }
    
    @Transactional(readOnly = true)
    public BookingDto getBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        return mapToBookingDto(booking);
    }
    
    @Transactional(readOnly = true)
    public BookingDto getBookingByNumber(String bookingNumber) {
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found: " + bookingNumber));
        return mapToBookingDto(booking);
    }
    
    @Transactional(readOnly = true)
    public Page<BookingDto> getCustomerBookings(Long customerId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByCustomerId(customerId, pageable);
        return bookings.map(this::mapToBookingDto);
    }
    
    @Transactional(readOnly = true)
    public Page<BookingDto> getPartnerBookings(Long partnerId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByPartnerId(partnerId, pageable);
        return bookings.map(this::mapToBookingDto);
    }
    
    private Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found with id: " + bookingId));
    }
    
    private String generateBookingNumber() {
        return "BK" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private void publishBookingCreatedEvent(Booking booking) {
        try {
            BookingCreatedEvent event = BookingCreatedEvent.builder()
                    .bookingId(booking.getId())
                    .bookingNumber(booking.getBookingNumber())
                    .customerId(booking.getCustomerId())
                    .serviceId(booking.getServiceId())
                    .scheduledAt(booking.getScheduledAt())
                    .totalPrice(booking.getTotalPrice())
                    .createdAt(booking.getCreatedAt())
                    .build();
            
            kafkaTemplate.send("booking-created-events", booking.getId().toString(), event);
        } catch (Exception e) {
            log.error("Failed to publish booking created event", e);
        }
    }
    
    private void publishPartnerAssignedEvent(Booking booking) {
        try {
            PartnerAssignedEvent event = PartnerAssignedEvent.builder()
                    .bookingId(booking.getId())
                    .bookingNumber(booking.getBookingNumber())
                    .partnerId(booking.getPartnerId())
                    .customerId(booking.getCustomerId())
                    .scheduledAt(booking.getScheduledAt())
                    .build();
            
            kafkaTemplate.send("partner-assigned-events", booking.getId().toString(), event);
        } catch (Exception e) {
            log.error("Failed to publish partner assigned event", e);
        }
    }
    
    private void publishStatusChangeEvent(Booking booking, BookingStatus oldStatus, BookingStatus newStatus) {
        try {
            BookingStatusChangedEvent event = BookingStatusChangedEvent.builder()
                    .bookingId(booking.getId())
                    .bookingNumber(booking.getBookingNumber())
                    .oldStatus(oldStatus.name())
                    .newStatus(newStatus.name())
                    .changedAt(LocalDateTime.now())
                    .build();
            
            kafkaTemplate.send("booking-status-changed-events", booking.getId().toString(), event);
        } catch (Exception e) {
            log.error("Failed to publish status change event", e);
        }
    }
    
    private void publishBookingCancelledEvent(Booking booking) {
        try {
            BookingCancelledEvent event = BookingCancelledEvent.builder()
                    .bookingId(booking.getId())
                    .bookingNumber(booking.getBookingNumber())
                    .customerId(booking.getCustomerId())
                    .partnerId(booking.getPartnerId())
                    .reason(booking.getCancellationReason())
                    .cancelledAt(booking.getCancelledAt())
                    .build();
            
            kafkaTemplate.send("booking-cancelled-events", booking.getId().toString(), event);
        } catch (Exception e) {
            log.error("Failed to publish booking cancelled event", e);
        }
    }
    
    private BookingDto mapToBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .bookingNumber(booking.getBookingNumber())
                .customerId(booking.getCustomerId())
                .serviceId(booking.getServiceId())
                .partnerId(booking.getPartnerId())
                .scheduledAt(booking.getScheduledAt())
                .status(booking.getStatus())
                .address(mapToBookingAddressDto(booking.getAddress()))
                .basePrice(booking.getBasePrice())
                .discount(booking.getDiscount())
                .tax(booking.getTax())
                .totalPrice(booking.getTotalPrice())
                .customerNotes(booking.getCustomerNotes())
                .cancellationReason(booking.getCancellationReason())
                .paymentStatus(booking.getPaymentStatus())
                .paymentMethod(booking.getPaymentMethod())
                .createdAt(booking.getCreatedAt())
                .completedAt(booking.getCompletedAt())
                .build();
    }
    
    private BookingAddress mapToBookingAddress(BookingAddressDto dto) {
        return BookingAddress.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .zipCode(dto.getZipCode())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .apartmentNumber(dto.getApartmentNumber())
                .landmark(dto.getLandmark())
                .build();
    }
    
    private BookingAddressDto mapToBookingAddressDto(BookingAddress address) {
        if (address == null) return null;
        return BookingAddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .apartmentNumber(address.getApartmentNumber())
                .landmark(address.getLandmark())
                .build();
    }
}
