package com.urbanclone.booking.service;

import com.urbanclone.booking.dto.BookingDto;
import com.urbanclone.booking.dto.BookingRequest;
import com.urbanclone.booking.entity.Booking;
import com.urbanclone.booking.entity.BookingStatus;
import com.urbanclone.booking.exception.BookingNotFoundException;
import com.urbanclone.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Booking Service Tests")
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingStateMachine stateMachine;

    @Mock
    private PricingService pricingService;

    @Mock
    private PartnerMatchingService partnerMatchingService;

    @InjectMocks
    private BookingService bookingService;

    private Booking testBooking;
    private BookingRequest bookingRequest;

    @BeforeEach
    void setUp() {
        testBooking = Booking.builder()
                .id(1L)
                .userId(100L)
                .serviceId(200L)
                .partnerId(300L)
                .status(BookingStatus.PENDING)
                .scheduledTime(LocalDateTime.now().plusDays(1))
                .address("123 Test St")
                .latitude(40.7128)
                .longitude(-74.0060)
                .estimatedPrice(BigDecimal.valueOf(50.00))
                .actualPrice(BigDecimal.valueOf(50.00))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bookingRequest = BookingRequest.builder()
                .userId(100L)
                .serviceId(200L)
                .scheduledTime(LocalDateTime.now().plusDays(1))
                .address("123 Test St")
                .latitude(40.7128)
                .longitude(-74.0060)
                .notes("Test booking")
                .build();
    }

    @Test
    @DisplayName("Should create booking successfully")
    void shouldCreateBooking() {
        // Given
        when(pricingService.calculatePrice(anyLong(), any())).thenReturn(BigDecimal.valueOf(50.00));
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // When
        BookingDto result = bookingService.createBooking(bookingRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(bookingRequest.getUserId());
        assertThat(result.getServiceId()).isEqualTo(bookingRequest.getServiceId());
        assertThat(result.getStatus()).isEqualTo(BookingStatus.PENDING);

        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Booking savedBooking = bookingCaptor.getValue();
        assertThat(savedBooking.getEstimatedPrice()).isEqualTo(BigDecimal.valueOf(50.00));
    }

    @Test
    @DisplayName("Should get booking by id successfully")
    void shouldGetBookingById() {
        // Given
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));

        // When
        BookingDto result = bookingService.getBookingById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testBooking.getId());
        assertThat(result.getStatus()).isEqualTo(testBooking.getStatus());
        verify(bookingRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when booking not found")
    void shouldThrowExceptionWhenBookingNotFound() {
        // Given
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> bookingService.getBookingById(1L))
                .isInstanceOf(BookingNotFoundException.class)
                .hasMessageContaining("Booking not found with id: 1");
    }

    @Test
    @DisplayName("Should get bookings by user id")
    void shouldGetBookingsByUserId() {
        // Given
        List<Booking> bookings = Arrays.asList(testBooking);
        when(bookingRepository.findByUserId(100L)).thenReturn(bookings);

        // When
        List<BookingDto> result = bookingService.getBookingsByUserId(100L);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(100L);
        verify(bookingRepository).findByUserId(100L);
    }

    @Test
    @DisplayName("Should get bookings by partner id")
    void shouldGetBookingsByPartnerId() {
        // Given
        List<Booking> bookings = Arrays.asList(testBooking);
        when(bookingRepository.findByPartnerId(300L)).thenReturn(bookings);

        // When
        List<BookingDto> result = bookingService.getBookingsByPartnerId(300L);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPartnerId()).isEqualTo(300L);
        verify(bookingRepository).findByPartnerId(300L);
    }

    @Test
    @DisplayName("Should confirm booking successfully")
    void shouldConfirmBooking() {
        // Given
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(stateMachine.canTransition(BookingStatus.PENDING, BookingStatus.CONFIRMED)).thenReturn(true);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // When
        BookingDto result = bookingService.confirmBooking(1L);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Booking confirmedBooking = bookingCaptor.getValue();
        assertThat(confirmedBooking.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
    }

    @Test
    @DisplayName("Should assign partner to booking")
    void shouldAssignPartner() {
        // Given
        Long partnerId = 400L;
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // When
        BookingDto result = bookingService.assignPartner(1L, partnerId);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Booking updatedBooking = bookingCaptor.getValue();
        assertThat(updatedBooking.getPartnerId()).isEqualTo(partnerId);
    }

    @Test
    @DisplayName("Should start booking successfully")
    void shouldStartBooking() {
        // Given
        testBooking.setStatus(BookingStatus.CONFIRMED);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(stateMachine.canTransition(BookingStatus.CONFIRMED, BookingStatus.IN_PROGRESS)).thenReturn(true);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // When
        BookingDto result = bookingService.startBooking(1L);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Booking startedBooking = bookingCaptor.getValue();
        assertThat(startedBooking.getStatus()).isEqualTo(BookingStatus.IN_PROGRESS);
        assertThat(startedBooking.getStartTime()).isNotNull();
    }

    @Test
    @DisplayName("Should complete booking successfully")
    void shouldCompleteBooking() {
        // Given
        testBooking.setStatus(BookingStatus.IN_PROGRESS);
        testBooking.setStartTime(LocalDateTime.now().minusHours(1));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(stateMachine.canTransition(BookingStatus.IN_PROGRESS, BookingStatus.COMPLETED)).thenReturn(true);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // When
        BookingDto result = bookingService.completeBooking(1L);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Booking completedBooking = bookingCaptor.getValue();
        assertThat(completedBooking.getStatus()).isEqualTo(BookingStatus.COMPLETED);
        assertThat(completedBooking.getEndTime()).isNotNull();
    }

    @Test
    @DisplayName("Should cancel booking successfully")
    void shouldCancelBooking() {
        // Given
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(stateMachine.canTransition(any(), eq(BookingStatus.CANCELLED))).thenReturn(true);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // When
        BookingDto result = bookingService.cancelBooking(1L, "Customer request");

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Booking cancelledBooking = bookingCaptor.getValue();
        assertThat(cancelledBooking.getStatus()).isEqualTo(BookingStatus.CANCELLED);
        assertThat(cancelledBooking.getCancellationReason()).isEqualTo("Customer request");
    }

    @Test
    @DisplayName("Should update actual price")
    void shouldUpdateActualPrice() {
        // Given
        BigDecimal actualPrice = BigDecimal.valueOf(55.00);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // When
        BookingDto result = bookingService.updateActualPrice(1L, actualPrice);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Booking updatedBooking = bookingCaptor.getValue();
        assertThat(updatedBooking.getActualPrice()).isEqualTo(actualPrice);
    }

    @Test
    @DisplayName("Should get bookings by status")
    void shouldGetBookingsByStatus() {
        // Given
        List<Booking> bookings = Arrays.asList(testBooking);
        when(bookingRepository.findByStatus(BookingStatus.PENDING)).thenReturn(bookings);

        // When
        List<BookingDto> result = bookingService.getBookingsByStatus(BookingStatus.PENDING);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(BookingStatus.PENDING);
        verify(bookingRepository).findByStatus(BookingStatus.PENDING);
    }
}
