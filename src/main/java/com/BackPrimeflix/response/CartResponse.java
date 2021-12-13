package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.CartDto;

public class CartResponse extends Response{
    //members
    CartDto cart;

    //getters and setters
    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }
}
