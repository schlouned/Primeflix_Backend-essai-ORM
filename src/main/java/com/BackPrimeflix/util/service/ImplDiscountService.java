package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.CategoryDto;
import com.BackPrimeflix.dto.DiscountDto;
import com.BackPrimeflix.model.CategoryEntity;
import com.BackPrimeflix.model.DiscountEntity;
import com.BackPrimeflix.repository.DiscountRepository;
import com.BackPrimeflix.util.ConvertDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("discountService")
public class ImplDiscountService implements DiscountService {
    //members
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private CategoryService categoryService;

    //methods
    public void save(final DiscountEntity discountEntity) {
        discountRepository.save(discountEntity);
    }

    public Integer checkIfNotTwoPromotionAtTheSameTimeByCategory(DiscountEntity discountEntity) {
        //check the category of the new promotion
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity = discountEntity.getCategory();

        //check if the start date of the new discount is between the start and end date of the other discount
        Set<DiscountEntity> formerDiscounts = categoryEntity.getDiscounts();
        for (DiscountEntity discount : formerDiscounts) {
            if (discountEntity.getStartDate().before(discount.getEndDate())) {
                return 1;
            }
        }
        return 0;
    }

    public List<DiscountEntity> getDiscounts() {
        return discountRepository.findAll();
    }

    public DiscountDto convertToDiscountDto(DiscountEntity discountEntity) {
        CategoryEntity categoryEntity = discountEntity.getCategory();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryEntity.getId().toString());
        categoryDto.setName(categoryEntity.getName());

        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(discountEntity.getId().toString());
        discountDto.setCategory(categoryDto);
        discountDto.setPercentage(discountEntity.getPercentage().toString());
        discountDto.setStartDate(discountEntity.getStartDate().toString());
        discountDto.setEndDate(discountEntity.getEndDate().toString());

        return discountDto;
    }

    public DiscountEntity convertToDiscountEntity(DiscountDto discountDto) throws ParseException {
        //recover the category entity
        CategoryDto categoryDto = discountDto.getCategory();
        CategoryEntity categoryEntity = categoryService.getCategoryById(Long.parseLong(categoryDto.getId()));

        //recover the discount entity
        DiscountEntity discountEntity = discountRepository.getDiscountEntityById(Long.parseLong(discountDto.getId()));
        discountEntity.setStartDate(ConvertDate.convertJsonDateToDate(discountDto.getStartDate()));
        discountEntity.setEndDate(ConvertDate.convertJsonDateToDate(discountDto.getEndDate()));
        discountEntity.setPercentage(Integer.parseInt(discountDto.getPercentage()));

        return discountEntity;
    }

    public void deleteDiscount(DiscountEntity discountEntity){
        discountRepository.delete(discountEntity);
    }

    public DiscountEntity getById(Long id){
        return discountRepository.getDiscountEntityById(id);
    }
}
