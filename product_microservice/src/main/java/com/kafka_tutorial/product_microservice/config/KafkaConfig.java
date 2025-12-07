package com.kafka_tutorial.product_microservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {
    // Create method responsible for create a new topic
    @Bean
    public NewTopic newTopic() {
        // To create a new topic, we use TopicBuilder class
        return TopicBuilder.name("product-created-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
}
