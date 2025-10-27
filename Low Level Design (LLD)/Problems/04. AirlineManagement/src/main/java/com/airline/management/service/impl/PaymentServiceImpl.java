package com.airline.management.service.impl;

import com.airline.management.dto.payment.PaymentRequest;
import com.airline.management.dto.payment.PaymentResponse;
import com.airline.management.entity.Booking;
import com.airline.management.entity.Payment;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.mapper.PaymentMapper;
import com.airline.management.repository.BookingRepository;
import com.airline.management.repository.PaymentRepository;
import com.airline.management.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + request.getBookingId()));

        Payment payment = paymentMapper.toEntity(request, booking);
        booking.setPayment(payment);

        paymentRepository.save(payment);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
