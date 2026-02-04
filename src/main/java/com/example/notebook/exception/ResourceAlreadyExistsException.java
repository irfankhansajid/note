package com.example.notebook.exception;


public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String s) {
        super(s);
    }
}
