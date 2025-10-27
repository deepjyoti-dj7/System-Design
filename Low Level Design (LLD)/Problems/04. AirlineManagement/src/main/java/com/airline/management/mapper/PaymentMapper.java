package com.airline.management.mapper;

import com.airline.management.dto.payment.PaymentRequest;
import com.airline.management.dto.payment.PaymentResponse;
import com.airline.management.entity.Booking;
import com.airline.management.entity.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest request, Booking booking) {
        return Payment.builder()
                .booking(booking)
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentTime(LocalDateTime.now())
                .status("PAID")
                .build();
    }

    public PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking() != null ? payment.getBooking().getId() : null)
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentTime(payment.getPaymentTime())
                .status(payment.getStatus())
                .build();
    }
}
