package com.BackPrimeflix.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class CartItemEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Integer quantity;
    @ManyToOne
    @JoinColumn( name="movie_id", nullable = false)
    private MovieEntity movie;
    @ManyToOne
    @JoinColumn( name="cart_id", nullable = false)
    private CartEntity cart;

    //constructor
    public CartItemEntity() {}

    //getters and setters
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public Integer getQuantity() {return quantity;}

    public void setQuantity(Integer quantity) {this.quantity = quantity;}

    public MovieEntity getMovie() {return movie;}

    public void setMovie(MovieEntity movie) {this.movie = movie;}

    public CartEntity getCart() {return cart;}

    public void setCart(CartEntity cart) {this.cart = cart;}
}
