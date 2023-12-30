package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.adapter.InventoryAdapter;
import com.ecommerce.inventoryservice.dto.InventoryDto;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final InventoryAdapter inventoryAdapter;

    public InventoryDto getInventory(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(
                () -> new RuntimeException("Inventory not found")
        );
        return inventoryAdapter.toInventoryDto(inventory);
    }
}
