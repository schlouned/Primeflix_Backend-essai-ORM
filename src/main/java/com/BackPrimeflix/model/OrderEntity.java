package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class OrderEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @OneToOne
    private CartEntity cartEntity;
    //@ManyToOne
    @ManyToOne
    @JoinColumn(name="deliveryType_id", nullable=false)
    private DeliveryTypeEntity deliveryTypeEntity;
    @ManyToOne
    @JoinColumn(name="orderState_id", nullable=false)
    private OrderStateEntity orderStateEntity;
    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private InvoiceEntity invoiceEntity;
    private Float cartCost;
    private Float deliveryCost;
    private Float totalCost;


    //constructor
    public OrderEntity() {
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public DeliveryTypeEntity getDeliveryTypeEntity() {
        return deliveryTypeEntity;
    }

    public void setDeliveryTypeEntity(DeliveryTypeEntity deliveryTypeEntity) {
        this.deliveryTypeEntity = deliveryTypeEntity;
    }

    public OrderStateEntity getOrderStateEntity() {
        return orderStateEntity;
    }

    public void setOrderStateEntity(OrderStateEntity orderStateEntity) {
        this.orderStateEntity = orderStateEntity;
    }

    public InvoiceEntity getInvoiceEntity() {
        return invoiceEntity;
    }

    public void setInvoiceEntity(InvoiceEntity invoiceEntity) {
        this.invoiceEntity = invoiceEntity;
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

    public void setTotalCost(Float totalPrice) {
        this.totalCost = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getCartCost() {
        return cartCost;
    }

    public void setCartCost(Float cartCost) {
        this.cartCost = cartCost;
    }
}
