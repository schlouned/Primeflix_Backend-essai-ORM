package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.AddressDto;

public class UserResponse extends Response{
    //members
    private String lastName;
    private String firstName;
    private AddressDto address;
    private String email;

    //constructor
    public UserResponse(){}

    //getter and setter

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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
