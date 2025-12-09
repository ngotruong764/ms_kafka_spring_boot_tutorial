package com.kafka_tutorial.product_microservice.service;

import com.kafka_tutorial.core.ProductCreatedEvent;
import com.kafka_tutorial.product_microservice.Models.CreateProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
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
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate
                .send("product-created-event-topic", productId, productCreatedEvent)
                .get();

        LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("Partition offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("Created product with id: {}", productId);

        return productId;
    }
}
