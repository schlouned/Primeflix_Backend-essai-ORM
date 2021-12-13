package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.InventoryEntity;
import com.BackPrimeflix.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
}
