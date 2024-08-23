package com.example.newsservice2.category.service;

public class CategoryEntityNotFound extends RuntimeException{
    public CategoryEntityNotFound() {
    }

    public CategoryEntityNotFound(String message) {
        super(message);
    }
}
