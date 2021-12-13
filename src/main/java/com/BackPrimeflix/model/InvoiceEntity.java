package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class InvoiceEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity orderEntity;

}
