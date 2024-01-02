package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.adapter.OrderAdapter;
import com.ecommerce.orderservice.dto.InventoryDto;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.exception.InventoryUnavailableException;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service class for handling order related operations.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderAdapter orderAdapter;
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final LoadBalancerClient loadBalancerClient;

    /**
     * Places an order.
     *
     * @param orderRequest the order request
     * @return the order request
     * @throws InventoryUnavailableException if the inventory is unavailable
     */
    public OrderRequest placeOrder(OrderRequest orderRequest) throws InventoryUnavailableException{
        Order order = orderAdapter.toOrder(orderRequest);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        List<InventoryDto> responseEntity = fetchInventory(skuCodes);

        if (isInventoryAvailable(responseEntity)){
            Order savedOrder = orderRepository.save(order);
            return orderAdapter.toOrderDto(savedOrder);
        }else{
            throw new InventoryUnavailableException("Item not in stock");
        }

    }

    /**
     * Checks if the inventory is available.
     *
     * @param inventoryList the inventory list
     * @return true if the inventory is available, false otherwise
     */
    private boolean isInventoryAvailable(List<InventoryDto> inventoryList) {
        if (inventoryList.isEmpty()){
            return false;
        }
        return inventoryList.stream().allMatch(inventoryDto -> inventoryDto.getStock() >= 0);
    }

    /**
     * Fetches the inventory.
     *
     * @param skuCodesQueryParam the SKU codes query parameter
     * @return the inventory
     */
    private List<InventoryDto> fetchInventory(List<String> skuCodesQueryParam) {

        return webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                    uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodesQueryParam).build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InventoryUnavailableException("Client error: " + response.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new Exception("Server error: " + response.statusCode()))
                )
                .bodyToFlux(InventoryDto.class)
                .collectList()
                .block();
    }

    /**
     * Gets an order.
     *
     * @param id the order ID
     * @return the order request
     */
    public OrderRequest getOrder(String id) {
        Order order = orderRepository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderAdapter.toOrderDto(order);
    }
}