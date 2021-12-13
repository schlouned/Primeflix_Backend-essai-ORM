package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.CartItemDto;

public class CartItemResponse extends Response{
    //members
    CartItemDto cartItemDto;

    //getters and setters
    public CartItemDto getCartItemDto() {
        return cartItemDto;
    }

    public void setCartItemDto(CartItemDto cartItemDto) {
        this.cartItemDto = cartItemDto;
    }
}
