package com.urbanclone.payment.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventListener {
    
    @KafkaListener(topics = "booking-completed-events", groupId = "payment-service")
    public void handleBookingCompleted(String event) {
        log.info("Received booking completed event: {}", event);
        // Process payment when booking is completed
    }
}
