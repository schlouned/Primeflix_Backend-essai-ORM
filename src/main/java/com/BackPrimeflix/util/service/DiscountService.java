package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.DiscountDto;
import com.BackPrimeflix.model.DiscountEntity;
import com.BackPrimeflix.model.UserEntity;

import java.text.ParseException;
import java.util.List;

public interface DiscountService {
    void save(final DiscountEntity discountEntity);
    Integer checkIfNotTwoPromotionAtTheSameTimeByCategory(DiscountEntity discountEntity);
    List<DiscountEntity> getDiscounts();
    DiscountDto convertToDiscountDto(DiscountEntity discountEntity);
    DiscountEntity convertToDiscountEntity(DiscountDto discountDto) throws ParseException;
    void deleteDiscount(DiscountEntity discountEntity);
    DiscountEntity getById(Long id);
}
