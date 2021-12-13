package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.InventoryDto;

import java.util.ArrayList;
import java.util.List;

public class InventoryResponse extends Response{
    //members
    private InventoryDto inventoryDto;
    private List<InventoryDto> inventories = new ArrayList<>();

    //getter and setter
    public InventoryDto getInventoryDto() {
        return inventoryDto;
    }

    public void setInventoryDto(InventoryDto inventoryDto) {
        this.inventoryDto = inventoryDto;
    }

    public List<InventoryDto> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoryDto> inventories) {
        this.inventories = inventories;
    }
}
