package com.airline.management.event.producer;

import com.airline.management.event.model.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("📤 Sending PaymentCompletedEvent to Kafka: {}", event);
        kafkaTemplate.send("payment.completed", event);
    }

    public void sendPaymentFailedEvent(PaymentCompletedEvent event) {
        log.info("📤 Sending PaymentFailedEvent to Kafka: {}", event);
        kafkaTemplate.send("payment.failed", event);
    }
}
