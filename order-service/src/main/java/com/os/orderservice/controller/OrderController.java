package com.os.orderservice.controller;

import com.os.orderservice.dto.OrderRequest;
import com.os.orderservice.service.Orderservice;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/os")
@RequiredArgsConstructor
public class OrderController {

    private final Orderservice orderservice;

    @PostMapping("/placeorder")
    @CircuitBreaker(name = "inventory" , fallbackMethod = "fallbackmethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderservice.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackmethod (OrderRequest orderRequest , RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }
}
