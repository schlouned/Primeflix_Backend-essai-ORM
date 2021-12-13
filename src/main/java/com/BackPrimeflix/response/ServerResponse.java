package com.BackPrimeflix.response;

//class use to prepare a response for the client
public class ServerResponse extends Response {
    //members
    private String userType;
    private String email;
    private String cartItemsNumber;

    //constructor
    public ServerResponse(){};

    //getter and setter
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getCartItemsNumber() {return cartItemsNumber;}

    public void setCartItemsNumber(String cartItemsNumber) {this.cartItemsNumber = cartItemsNumber;}
}
