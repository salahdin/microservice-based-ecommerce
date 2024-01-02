package com.ecommerce.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class AppConfig {
    @Bean
    //@LoadBalanced TODO: figure out why this is not working
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}