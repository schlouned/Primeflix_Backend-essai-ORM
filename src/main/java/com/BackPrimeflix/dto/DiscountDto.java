package com.BackPrimeflix.dto;

import java.io.Serializable;
import java.util.Date;

public class DiscountDto implements Serializable {
    //members
    private String id;
    private String percentage;
    private String startDate;
    private String endDate;
    private CategoryDto category;

    //constructor
    public DiscountDto() {
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
