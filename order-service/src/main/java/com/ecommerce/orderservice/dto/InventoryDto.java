package com.ecommerce.orderservice.dto;

import lombok.Data;

@Data
public class InventoryDto {
    private String skuCode;
    private Integer stock;
}
