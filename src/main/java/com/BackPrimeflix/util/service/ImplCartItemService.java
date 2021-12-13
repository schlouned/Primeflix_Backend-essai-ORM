package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.CartItemDto;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.CartItemEntity;
import com.BackPrimeflix.model.MovieEntity;
import com.BackPrimeflix.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("cartItemService")
public class ImplCartItemService implements CartItemService{
    //members
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MovieService movieService;

    //methods
    @Override
    public void save(final CartItemEntity cartItemEntity){
        cartItemRepository.save(cartItemEntity);
    }

    public CartItemDto convertToCartItemDto(CartItemEntity cartItemEntity){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItemEntity.getId().toString());
        cartItemDto.setDate(cartItemEntity.getDate());
        cartItemDto.setMovie(movieService.convertToMovieDto(cartItemEntity.getMovie()));
        cartItemDto.setQuantity(cartItemEntity.getQuantity());
        //no cart: no need for the moment
        return cartItemDto;
    }

    public CartItemEntity getCartItemEntityById(Long id){
        return cartItemRepository.findCartItemEntitiesById(id);
    }

    public void deleteCartItem(CartItemEntity cartItemEntity){
        cartItemRepository.delete(cartItemEntity);
    }

    public CartItemEntity getCartItemEntityByMovieAndCart(MovieEntity movie, CartEntity cart){
        return cartItemRepository.findCartItemEntityByMovieAndCart(movie, cart);
    }

    public Set<CartItemEntity> getCartItemsOrderByMovie(CartEntity cartEntity){
        return cartItemRepository.findCartItemEntitiesByCartOrderByMovie(cartEntity);
    }
}
