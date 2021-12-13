package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.CategoryDto;


import java.util.List;

public class CategoryResponse extends Response{
    //members
    private List<CategoryDto> categories;

    //getters setters
    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
