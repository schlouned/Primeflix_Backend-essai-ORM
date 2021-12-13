package com.BackPrimeflix.dto;

import java.io.Serializable;

public class PaypalPaymentDto implements Serializable {
    //members
    private String orderId;
    private String paymentId;
    private String payerId;

    //constructor
    public PaypalPaymentDto() {
    }

    //getter ans setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }
}
