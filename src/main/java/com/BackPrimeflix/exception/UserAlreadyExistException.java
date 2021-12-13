package com.BackPrimeflix.exception;

//if the user already exist
public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super();
    }
    public UserAlreadyExistException(String message) {
        super(message);
    }
    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
