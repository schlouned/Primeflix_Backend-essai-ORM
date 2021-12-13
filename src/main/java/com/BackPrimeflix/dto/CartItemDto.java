package com.BackPrimeflix.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CartItemDto implements Serializable, Comparable<CartItemDto> {
    //members
    private String id;
    private Date date;
    private Integer quantity;
    private MovieDto movie;
    private CartDto cart;

    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }

    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }

    //method
    @Override
    public int compareTo(CartItemDto cartItemDto) {
        return this.id.compareTo(cartItemDto.id);
    }

}
