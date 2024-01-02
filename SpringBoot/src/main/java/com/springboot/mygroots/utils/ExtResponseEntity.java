package com.springboot.mygroots.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Custom response entity to include a message in the response body
 */
public class ExtResponseEntity<T> extends ResponseEntity<ExtResponse<T>> {

    public ExtResponseEntity(T body, String message, HttpStatus status) {
        super(new ExtResponse<>(body, message), status);
    }

    public ExtResponseEntity(String message, HttpStatus status) {
        super(new ExtResponse<>(null, message), status);
    }

    public ExtResponseEntity(T body, HttpStatus status) {
        super(new ExtResponse<>(body, null), status);
    }
}
