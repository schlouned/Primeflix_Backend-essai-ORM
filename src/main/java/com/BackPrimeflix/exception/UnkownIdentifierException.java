package com.BackPrimeflix.exception;


//In case client account does not exists in the system for a given email

public class UnkownIdentifierException extends Exception {
    public UnkownIdentifierException() {
        super();
    }
    public UnkownIdentifierException(String message) {
        super(message);
    }
    public UnkownIdentifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
