package com.example.newsservice2.user.service;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String format) {
        super(format);
    }
}
