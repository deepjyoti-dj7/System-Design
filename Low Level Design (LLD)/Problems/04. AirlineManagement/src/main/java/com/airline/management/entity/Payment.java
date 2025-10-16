package com.airline.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private double amount;

    private String paymentMethod; // e.g., "CREDIT_CARD"

    private LocalDateTime paymentTime;

    private String status; // e.g., "PAID", "FAILED"
}
