package com.BackPrimeflix.dto;

import java.io.Serializable;

public class ProductorDto implements Serializable {
    private String lastName;
    private String firstName;

    public ProductorDto() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
