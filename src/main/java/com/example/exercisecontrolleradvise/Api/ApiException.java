package com.example.exercisecontrolleradvise.Api;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
