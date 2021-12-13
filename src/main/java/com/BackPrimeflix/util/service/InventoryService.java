package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.InventoryDto;
import com.BackPrimeflix.model.InventoryEntity;
import com.BackPrimeflix.model.OrderEntity;

import java.util.List;

public interface InventoryService {
    InventoryEntity save(InventoryEntity inventoryEntity);
    List<InventoryEntity> getAll();
    InventoryDto convertToInventoryDto(InventoryEntity inventoryEntity);
}
