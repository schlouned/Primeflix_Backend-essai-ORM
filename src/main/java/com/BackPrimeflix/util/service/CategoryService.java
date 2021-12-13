package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.CategoryDto;
import com.BackPrimeflix.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAllCategories();
    CategoryDto convertToCategoryDto(CategoryEntity categoryEntity);
    CategoryEntity getCategoryById(Long id);
}
