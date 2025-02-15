package com.example.learntodoback.exception;

public class AuthException extends RuntimeException {

    public AuthException(String errorMessage) {
        super(errorMessage);
    }
}
