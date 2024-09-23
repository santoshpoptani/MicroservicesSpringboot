package com.is.inventoryservice.controller;

import com.is.inventoryservice.service.Inventoryservice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/is")
@RequiredArgsConstructor
public class InventoryControl {

    private final Inventoryservice inventoryservice;

    @PostMapping("/item/{sku-code}")
    public boolean isInStock(@PathVariable("sku-code") String skuCode){
        return inventoryservice.isInStock(skuCode);
    }
}
