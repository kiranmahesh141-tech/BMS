package com.example.bookstore.exception;

public class InvalidBookException extends RuntimeException {
    public InvalidBookException(String message) {
        super(message);
    }
}
