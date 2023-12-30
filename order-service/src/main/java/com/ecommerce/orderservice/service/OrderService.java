package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.adapter.OrderAdapter;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderAdapter orderAdapter;
    private final OrderRepository orderRepository;
    public OrderRequest placeOrder(OrderRequest orderRequest) {
        Order order = orderAdapter.toOrder(orderRequest);
        Order savedOrder = orderRepository.save(order);
        return orderAdapter.toOrderDto(savedOrder);
    }

    public OrderRequest getOrder(String id) {
        Order order = orderRepository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderAdapter.toOrderDto(order);
    }
}
