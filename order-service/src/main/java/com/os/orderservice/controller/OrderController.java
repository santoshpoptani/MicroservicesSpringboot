package com.os.orderservice.controller;

import com.os.orderservice.dto.OrderRequest;
import com.os.orderservice.service.Orderservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/os")
@RequiredArgsConstructor
public class OrderController {

    private final Orderservice orderservice;

    @PostMapping("/placeorder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
        orderservice.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed");
    }
}
