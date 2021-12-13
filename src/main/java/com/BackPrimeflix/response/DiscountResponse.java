package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.DiscountDto;
import com.BackPrimeflix.dto.MovieDto;

import java.util.List;

public class DiscountResponse extends Response{
    //members
    private List<DiscountDto> discounts;

    //getters setters
    public List<DiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDto> discounts) {
        this.discounts = discounts;
    }
}
