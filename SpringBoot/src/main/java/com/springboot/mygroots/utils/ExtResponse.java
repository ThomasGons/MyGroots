package com.springboot.mygroots.utils;

/**
 * Wrapper class to include both the response body and a message
 */
public class ExtResponse<T> {
    private final T body;
    private final String message;

    public ExtResponse(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }
}
