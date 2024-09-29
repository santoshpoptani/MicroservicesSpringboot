package com.is.inventoryservice.controller;

import com.is.inventoryservice.dto.InventoryResponse;
import com.is.inventoryservice.service.Inventoryservice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/is")
@RequiredArgsConstructor
public class InventoryControl {

    private final Inventoryservice inventoryservice;

    @GetMapping("/item")
    public List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCode){
        return inventoryservice.isInStock(skuCode);
    }
}
