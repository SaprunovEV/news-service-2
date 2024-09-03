package com.example.newsservice2.user.input.web.v1.controller;

import com.example.newsservice2.user.input.web.v1.model.*;
import com.example.newsservice2.user.mapper.UserMapper;
import com.example.newsservice2.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    @GetMapping
    public ResponseEntity<UserListResponse> getAllUser(@Valid UserFilter filter) {
        return ResponseEntity.ok(mapper.listEntitiesToResponseList(service.findAll(filter)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createNewUser(@RequestBody @Valid UserPayload payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.entityToResponse(service.createUser(payload))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid UserId id, @RequestBody @Valid UserPayload payload) {
        return ResponseEntity.ok(mapper.entityToResponse(service.updateUser(id.getId(), mapper.payloadToEntity(payload))));
    }
}
