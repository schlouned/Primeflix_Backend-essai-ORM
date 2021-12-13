package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.ActorDto;
import com.BackPrimeflix.dto.ProductorDto;
import com.BackPrimeflix.dto.UserDto;

import java.util.List;

public class PersonResponse extends Response{
    //members
    List<UserDto> users;
    List<ActorDto> actors;
    List<ProductorDto>productors;

    //constructor
    public PersonResponse() {
    }

    //getters setters
    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }

    public List<ActorDto> getActors() {
        return actors;
    }

    public void setActors(List<ActorDto> actors) {
        this.actors = actors;
    }

    public List<ProductorDto> getProductors() {
        return productors;
    }

    public void setProductors(List<ProductorDto> productors) {
        this.productors = productors;
    }
}
