package com.example.notebook.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String s) {
        super(s);
    }
}
