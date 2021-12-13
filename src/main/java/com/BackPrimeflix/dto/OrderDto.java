package com.BackPrimeflix.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderDto implements Serializable {
    //members
    private String id;
    private CartDto cart;
    private String date;
    private String deliveryType;
    private String orderState;
    private Float cartCost;
    private Float deliveryCost;
    private Float totalCost;

    //getters and setters
    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Float getCartCost() {
        return cartCost;
    }

    public void setCartCost(Float cartCost) {
        this.cartCost = cartCost;
    }

    public Float getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Float deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
