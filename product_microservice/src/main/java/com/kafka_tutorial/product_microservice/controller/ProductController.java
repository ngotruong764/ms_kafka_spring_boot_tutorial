package com.kafka_tutorial.product_microservice.controller;

import com.kafka_tutorial.product_microservice.Models.CreateProductModel;
import com.kafka_tutorial.product_microservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody CreateProductModel product) throws Exception {
        String productId = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
