package com.example.newsservice2.category.input.web.v1.controller;

import com.example.newsservice2.category.input.web.v1.model.CategoryErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CategoryControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CategoryErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        var errorResponse = new CategoryErrorResponse();

        List<String> errorList = new ArrayList<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String defaultMessage = objectError.getDefaultMessage();
            errorList.add(defaultMessage);
        }

        String errorMessage = String.join("; ", errorList);

        errorResponse.setMessage(errorMessage);

        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }
}
