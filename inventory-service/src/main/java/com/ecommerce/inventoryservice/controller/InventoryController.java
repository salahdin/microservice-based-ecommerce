package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.InventoryDto;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto getInventory(@PathVariable("skuCode") String skuCode){
        return inventoryService.getInventory(skuCode);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDto> getInventories(@RequestParam("skuCodes") List<String> skuCodes){
        return inventoryService.getInventories(skuCodes);
    }

}
