package com.ecommerce.orderservice.adapter;

import com.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAdapter {

    final OrderLineItemsAdapter orderLineItemsAdapter;

    public OrderRequest toOrderDto(Order order) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderLineItemsList(order.getOrderLineItemsList().stream().map(orderLineItems -> {
            OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
            orderLineItemsDto.setPrice(orderLineItems.getPrice());
            orderLineItemsDto.setSkuCode(orderLineItems.getSkuCode());
            orderLineItemsDto.setQuantity(orderLineItems.getQuantity());
            return orderLineItemsDto;
        }).toList());
        return orderRequest;
    }

    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        orderRequest.getOrderLineItemsList().forEach(orderLineItemsDto -> {
            order.getOrderLineItemsList().add(orderLineItemsAdapter.toOrderLineItems(orderLineItemsDto));
        });
        return order;
    }
}
