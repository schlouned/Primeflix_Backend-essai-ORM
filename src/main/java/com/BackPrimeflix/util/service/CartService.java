package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.CartDto;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.UserEntity;

public interface CartService {
    CartEntity getCartByUser(UserEntity userEntity);
    CartDto convertToCartDto(CartEntity cartEntity);
    CartEntity save(CartEntity cartEntity);
}
