package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findCategoryEntityByNameEquals(String categoryName);

}
