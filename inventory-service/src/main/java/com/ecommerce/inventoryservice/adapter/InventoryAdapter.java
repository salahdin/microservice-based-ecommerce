package com.ecommerce.inventoryservice.adapter;

import com.ecommerce.inventoryservice.dto.InventoryDto;
import com.ecommerce.inventoryservice.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryAdapter {

    public InventoryDto toInventoryDto(Inventory inventory) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setSkuCode(inventory.getSkuCode());
        inventoryDto.setStock(inventory.getStock());
        return inventoryDto;
    }

    public Inventory toInventory(InventoryDto inventoryDto) {
            Inventory inventory = new Inventory();
            inventory.setSkuCode(inventoryDto.getSkuCode());
            inventory.setStock(inventoryDto.getStock());
            return inventory;
    }

}
