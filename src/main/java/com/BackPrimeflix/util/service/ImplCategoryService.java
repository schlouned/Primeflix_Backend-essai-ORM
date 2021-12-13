package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.CategoryDto;
import com.BackPrimeflix.model.CategoryEntity;
import com.BackPrimeflix.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class ImplCategoryService implements CategoryService{
    //members
    @Autowired
    CategoryRepository categoryRepository;

    //methods
    //find all categories
    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }

    //convert to dto
    public CategoryDto convertToCategoryDto(CategoryEntity categoryEntity){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryEntity.getId().toString());
            categoryDto.setName(categoryEntity.getName());
        return categoryDto;
    }

    //find category by id
    public CategoryEntity getCategoryById(Long id){
        return categoryRepository.getById(id);
    }
}
