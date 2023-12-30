package com.ecommerce.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryDto {
    private String skuCode;
    private Integer stock;
}
