package com.BackPrimeflix.exception;

public class PersonCrudCustomException extends RuntimeException{
    public PersonCrudCustomException(String message) {
        super(message);
    }
}
