package com.BackPrimeflix.dto;

import java.io.Serializable;
import java.util.List;

public class CategoryDto implements Serializable {
    //members
    private String id;
    private String name;
    //try to get just the current discount
    private DiscountDto discount;

    //constructor
    public CategoryDto() {
    }

    //getters setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountDto getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDto discount) {
        this.discount = discount;
    }
}
