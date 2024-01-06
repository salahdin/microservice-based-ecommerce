package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest){
        OrderRequest orderRequest1 = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderRequest1, HttpStatus.CREATED);
    }

    public ResponseEntity<String> fallback(OrderRequest orderRequest, Exception e){
        return new ResponseEntity<>("Service is down. Please try again later", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable String id){
        try{
            OrderRequest orderRequest = orderService.getOrder(id);
            return new ResponseEntity<>(orderRequest, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
