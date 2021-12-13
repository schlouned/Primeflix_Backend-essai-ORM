package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.InventoryDto;
import com.BackPrimeflix.model.InventoryEntity;
import com.BackPrimeflix.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("inventoryService")
public class ImplInventoryService implements InventoryService{
    //members
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private MovieService movieService;

    //methods
    @Override
    public InventoryEntity save(InventoryEntity inventoryEntity) {
        return inventoryRepository.save(inventoryEntity);
    }

    public List<InventoryEntity> getAll(){
        return inventoryRepository.findAll();
    }

    public InventoryDto convertToInventoryDto(InventoryEntity inventoryEntity){
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setId(inventoryEntity.getId().toString());
        inventoryDto.setDate(inventoryEntity.getInventoryDate().toString());
        inventoryDto.setMovie(movieService.convertToMovieDto(inventoryEntity.getMovie()));
        inventoryDto.setStockBeforeInventory(inventoryEntity.getStockBeforeInventory().toString());
        inventoryDto.setStockAfterInventory(inventoryEntity.getStockAfterInventory().toString());
        inventoryDto.setDifference(inventoryEntity.getDifference().toString());

        return inventoryDto;
    }
}
