package com.example.newsservice2.user.input.web.v1.controller;

import com.example.newsservice2.user.input.web.v1.model.UserErrorResponse;
import com.example.newsservice2.user.service.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "com.example.newsservice2.user.input.web.v1.controller")
public class UserRestControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        UserErrorResponse response = new UserErrorResponse();
        response.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        var errorResponse = new UserErrorResponse();

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
