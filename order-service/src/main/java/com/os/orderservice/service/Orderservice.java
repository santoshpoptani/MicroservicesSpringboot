package com.os.orderservice.service;

import com.os.orderservice.config.WebClientConfig;
import com.os.orderservice.dto.OrderLineItemsRequest;
import com.os.orderservice.dto.OrderRequest;
import com.os.orderservice.model.InventoryResponse;
import com.os.orderservice.model.Order;
import com.os.orderservice.model.OrderLineItems;
import com.os.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Orderservice {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsRequests().stream()
                .map(orderlineitemDto -> mapToDto(orderlineitemDto))
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodeList = order.getOrderLineItemsList().stream()
                .map(item -> item.getSkuCode())
                .toList();
        /* Call Invnetory Services , and place order if product is in stock */
        InventoryResponse[] inventoryResponses = webClient
                .get()
                .uri("http://localhost:8082/api/v1/is/item",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean match = Arrays.stream(inventoryResponses)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if(match){
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock , Please try again later");
        }



    }

    private OrderLineItems mapToDto(OrderLineItemsRequest orderlineitemDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderlineitemDto.getPrice());
        orderLineItems.setQuantity(orderlineitemDto.getQuantity());
        orderLineItems.setSkuCode(orderlineitemDto.getSkuCode());
        return orderLineItems;
    }
}
