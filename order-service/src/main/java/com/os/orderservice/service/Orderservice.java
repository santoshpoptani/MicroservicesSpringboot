package com.os.orderservice.service;

import com.os.orderservice.dto.OrderLineItemsRequest;
import com.os.orderservice.dto.OrderRequest;
import com.os.orderservice.model.Order;
import com.os.orderservice.model.OrderLineItems;
import com.os.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Orderservice {

    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsRequests().stream()
                .map(orderlineitemDto -> mapToDto(orderlineitemDto))
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsRequest orderlineitemDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderlineitemDto.getPrice());
        orderLineItems.setQuantity(orderlineitemDto.getQuantity());
        orderLineItems.setSkuCode(orderlineitemDto.getSkuCode());
        return orderLineItems;
    }
}
