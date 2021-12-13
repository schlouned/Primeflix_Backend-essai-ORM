package com.BackPrimeflix.dto;

import java.io.Serializable;

public class PaymentDto implements Serializable {
    //members
    private String token;
    private String orderId;

    //getter and setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
