package com.kafka_tutorial.email_notification_ms.handler;

import com.kafka_tutorial.core.ProductCreatedEvent;
import com.kafka_tutorial.email_notification_ms.error.NotRetryableException;
import com.kafka_tutorial.email_notification_ms.error.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@KafkaListener(topics = "product-created-event-topic")
public class ProductCreateEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;

    public ProductCreateEventHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Handle event
    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent) {
//        if (true) throw new RetryableException("An error occurred. No nee to consume this message again.");

        LOGGER.info("Received a new event: {}", productCreatedEvent.getTitle());
        // Send HTTP request to external microservice
        String requestUrl = "http://localhost:8080";
        try {
            ResponseEntity<String> response =  restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                LOGGER.info("Received a new event: {}", productCreatedEvent.getTitle());
            }
        } catch (ResourceAccessException e){
            LOGGER.error("ResourceAccessException", e);
            throw new RetryableException(e);
        }


    }
}
