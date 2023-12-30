package com.ecommerce.orderservice.adapter;

import com.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.ecommerce.orderservice.model.OrderLineItems;
import org.springframework.stereotype.Component;

@Component
public class OrderLineItemsAdapter {

    public OrderLineItemsDto toOrderLineItemsDto(OrderLineItems orderLineItems) {
        OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
        orderLineItemsDto.setPrice(orderLineItems.getPrice());
        orderLineItemsDto.setSkuCode(orderLineItems.getSkuCode());
        orderLineItemsDto.setQuantity(orderLineItems.getQuantity());
        return orderLineItemsDto;
    }

    public OrderLineItems toOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
