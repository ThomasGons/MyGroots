package com.springboot.mygroots.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Custom response entity to add a message to the response
 */
public class ExtResponseEntity<T> extends ResponseEntity<T> {
    private final String message;

    public ExtResponseEntity(T body, String message, HttpStatus status) {
        super(body, null, status);
        this.message = message;
    }

    public ExtResponseEntity(String message, HttpStatus status) {
        super(null, null, status);
        this.message = message;
    }

    public ExtResponseEntity(T body, HttpStatus status) {
        super(body, null, status);
        this.message = null;
    }

    public String getMessage() {
        return message;
    }
}
