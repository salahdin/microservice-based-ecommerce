package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.adapter.OrderAdapter;
import com.ecommerce.orderservice.dto.InventoryDto;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.exception.InventoryUnavailableException;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderAdapter orderAdapter;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public OrderRequest placeOrder(OrderRequest orderRequest) throws InventoryUnavailableException{
        Order order = orderAdapter.toOrder(orderRequest);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        String skuCodesQueryParam = String.join(",", skuCodes);
        ResponseEntity<List<InventoryDto>> responseEntity = fetchInventory(skuCodesQueryParam);

        if (isInventoryAvailable(responseEntity.getBody())){
            Order savedOrder = orderRepository.save(order);
            return orderAdapter.toOrderDto(savedOrder);
        }else{
            throw new InventoryUnavailableException("Item not in stock");
        }

    }

    private boolean isInventoryAvailable(List<InventoryDto> inventoryList) {
        if (inventoryList.isEmpty()){
            return false;
        }
        return inventoryList.stream().allMatch(inventoryDto -> inventoryDto.getStock() >= 0);
    }

    private ResponseEntity<List<InventoryDto>> fetchInventory(String skuCodesQueryParam) {
        String inventoryUrl = inventoryServiceUrl + "?skuCodes=" + skuCodesQueryParam;
        return restTemplate.exchange(
                inventoryUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryDto>>() {}
        );
    }

    public OrderRequest getOrder(String id) {
        Order order = orderRepository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderAdapter.toOrderDto(order);
    }
}
