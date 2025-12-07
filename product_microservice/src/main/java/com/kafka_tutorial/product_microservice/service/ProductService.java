package com.kafka_tutorial.product_microservice.service;

import com.kafka_tutorial.product_microservice.Models.CreateProductModel;

public interface ProductService {
    String createProduct(CreateProductModel product);
}
