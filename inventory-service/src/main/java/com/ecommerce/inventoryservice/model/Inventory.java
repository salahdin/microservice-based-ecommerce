package com.ecommerce.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;
    private String skuCode;
    private Integer stock;

}
