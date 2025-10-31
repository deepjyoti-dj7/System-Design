package com.airline.management.service;

import com.airline.management.dto.booking.BookingRequest;
import com.airline.management.dto.booking.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBookingById(Long id);
    List<BookingResponse> getAllBookings();
    void deleteBooking(Long id);
}
