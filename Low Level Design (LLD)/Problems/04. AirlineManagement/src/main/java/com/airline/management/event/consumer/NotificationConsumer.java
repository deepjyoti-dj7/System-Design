package com.airline.management.event.consumer;

import com.airline.management.event.model.BookingCreatedEvent;
import com.airline.management.event.model.PaymentCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    @KafkaListener(topics = "booking.created", groupId = "notification-group")
    public void consumeBookingCreatedEvent(BookingCreatedEvent event) {
        log.info("📩 Received BookingCreatedEvent: {}", event);
        // TODO: Send email or push notification
    }

    @KafkaListener(topics = "payment.completed", groupId = "notification-group")
    public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("📩 Received PaymentCompletedEvent: {}", event);
        // TODO: Send payment confirmation
    }

    @KafkaListener(topics = "payment.failed", groupId = "notification-group")
    public void consumePaymentFailedEvent(PaymentCompletedEvent event) {
        log.warn("⚠️ Received PaymentFailedEvent: {}", event);
        // TODO: Send failure email
    }
}
