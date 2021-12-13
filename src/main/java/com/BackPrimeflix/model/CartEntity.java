package com.BackPrimeflix.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CartEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @OneToMany( targetEntity=CartItemEntity.class, mappedBy= "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItemEntity> cartItemEntities = new HashSet<>();
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;
    @OneToOne( cascade = CascadeType.ALL, fetch=FetchType.EAGER )
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
    private Boolean openStatus;

    //constructor
    public CartEntity() {}

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<CartItemEntity> getCartItemEntities() {
        return cartItemEntities;
    }

    public void setCartItemEntities(Set<CartItemEntity> cartItemEntities) {
        this.cartItemEntities = cartItemEntities;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public Boolean getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Boolean open) {
        this.openStatus = open;
    }
}
