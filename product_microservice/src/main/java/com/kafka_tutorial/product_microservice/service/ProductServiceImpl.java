package com.kafka_tutorial.product_microservice.service;

import com.kafka_tutorial.product_microservice.Models.CreateProductModel;
import com.kafka_tutorial.product_microservice.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductModel product) throws Exception {
        String productId = UUID.randomUUID().toString();
        // TODO: Persist product detail into database table before publishing an Event

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, product.getTitle(),
                product.getPrice(), product.getQuantity());

        LOGGER.info("Before publishing a ProductCreateEvent");

        // Send message sync
        SendResult<String, String> result = kafkaTemplate
                .send("product-created-event-topic", productId, objectMapper.writeValueAsString(productCreatedEvent))
                .get();

        LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("Partition offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("Created product with id: {}", productId);

        return productId;
    }
}
