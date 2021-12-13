package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.CartDto;
import com.BackPrimeflix.dto.CartItemDto;
import com.BackPrimeflix.dto.UserDto;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.CartItemEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("cartService")
public class ImplCartService implements CartService{
    //members
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;

    //methods
    public CartEntity getCartByUser(UserEntity userEntity){
        CartEntity cartEntity = userEntity.getOpenCart();
        return cartEntity;
    }

    public CartDto convertToCartDto(CartEntity cartEntity){
        CartDto cartDto = new CartDto();
        cartDto.setId(cartEntity.getId().toString());
        cartDto.setDate(cartEntity.getDate());
        //cartDto.setTotalPrice(cartEntity.getTotalPrice().toString()); // comment because null
        //loop
        Set<CartItemDto> cartItemDtos = new HashSet<>();
        for(CartItemEntity cartItemEntity: cartEntity.getCartItemEntities()){
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto = cartItemService.convertToCartItemDto(cartItemEntity);
            cartItemDtos.add(cartItemDto);
        }
        cartDto.setCartItems(cartItemDtos);
        UserDto userDto = userService.convertToUserDto(cartEntity.getUser());
        cartDto.setUser(userDto);
        //not the user: no need for the moment
        return cartDto;
    }

    public CartEntity save(CartEntity cartEntity){
        return cartRepository.save(cartEntity);
    }
}
