package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.CartItemDto;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.CartItemEntity;
import com.BackPrimeflix.model.MovieEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CartItemService {
    void save(final CartItemEntity cartItemEntity);
    CartItemDto convertToCartItemDto(CartItemEntity cartItemEntity);
    CartItemEntity getCartItemEntityById(Long id);
    void deleteCartItem(CartItemEntity cartItemEntity);
    CartItemEntity getCartItemEntityByMovieAndCart(MovieEntity movie, CartEntity cart);
    Set<CartItemEntity> getCartItemsOrderByMovie(CartEntity cartEntity);

}
