package com.example.notebook.exception;


public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String s) {
        super(s);
    }
}
