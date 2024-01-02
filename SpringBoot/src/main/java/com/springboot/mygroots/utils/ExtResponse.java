package com.springboot.mygroots.utils;

/**
 * Wrapper class to include both the response body and a message
 */
public class ExtResponse<T> {
    private final T body;
    private final String errorMessage;

    public ExtResponse(T body, String message) {
        this.body = body;
        this.errorMessage = message;
    }

    public T getBody() {
        return body;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
