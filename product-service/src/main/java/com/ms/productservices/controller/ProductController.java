package com.ms.productservices.controller;

import com.ms.productservices.dto.ProductRequest;
import com.ms.productservices.dto.ProductResponse;
import com.ms.productservices.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Product Created");
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
