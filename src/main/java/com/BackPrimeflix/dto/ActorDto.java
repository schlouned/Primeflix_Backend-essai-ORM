package com.BackPrimeflix.dto;

import java.io.Serializable;

public class ActorDto implements Serializable {
    private String lastName;
    private String firstName;

    public ActorDto() {
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
