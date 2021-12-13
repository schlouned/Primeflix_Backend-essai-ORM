package com.BackPrimeflix.exception;

public class AddressDontExistCustomException extends RuntimeException{
    public AddressDontExistCustomException(String message) {
        super(message);
    }
}
