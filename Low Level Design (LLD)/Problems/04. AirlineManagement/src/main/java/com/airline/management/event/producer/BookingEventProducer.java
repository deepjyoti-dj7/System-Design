package com.airline.management.event.producer;

import com.airline.management.event.model.BookingCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendBookingCreatedEvent(BookingCreatedEvent event) {
        log.info("📤 Sending BookingCreatedEvent to Kafka: {}", event);
        kafkaTemplate.send("booking.created", event);
    }
}
