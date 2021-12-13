package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="person_type")
public abstract class PersonEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate the primary key automatically
    private Long id;
    private String firstName;
    private String lastName;
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="address_id")
    private AddressEntity addressEntity;

    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }
}
