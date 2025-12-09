package com.kafka_tutorial.email_notification_ms.handler;

import com.kafka_tutorial.core.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "product-created-event-topic")
public class ProductCreateEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // Handle event
    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent) {
        LOGGER.info("Received a new event: {}", productCreatedEvent.getTitle());
    }
}
