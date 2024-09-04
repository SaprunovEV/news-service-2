package com.example.newsservice2.user.input.web.v1.controller;

import com.example.newsservice2.user.input.web.v1.annotation.openApi.CreateNewUserDock;
import com.example.newsservice2.user.input.web.v1.annotation.openApi.DeleteUserDock;
import com.example.newsservice2.user.input.web.v1.annotation.openApi.GetPageUsersDock;
import com.example.newsservice2.user.input.web.v1.annotation.openApi.UpdateUserDock;
import com.example.newsservice2.user.input.web.v1.model.*;
import com.example.newsservice2.user.mapper.UserMapper;
import com.example.newsservice2.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Users V1", description = "Users API version V1")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    @GetMapping
    @GetPageUsersDock
    public ResponseEntity<UserListResponse> getAllUser(@Valid UserFilter filter) {
        return ResponseEntity.ok(mapper.listEntitiesToResponseList(service.findAll(filter.getPageNumber(), filter.getPageSize())));
    }

    @PostMapping
    @CreateNewUserDock
    public ResponseEntity<UserResponse> createNewUser(@RequestBody @Valid UserPayload payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.entityToResponse(service.createUser(mapper.payloadToEntity(payload)))
        );
    }

    @PutMapping("/{id}")
    @UpdateUserDock
    public ResponseEntity<UserResponse> updateUser(@Valid UserId id, @RequestBody @Valid UserPayload payload) {
        return ResponseEntity.ok(mapper.entityToResponse(service.updateUser(id.getId(), mapper.payloadToEntity(payload))));
    }

    @DeleteMapping("/{id}")
    @DeleteUserDock
    public ResponseEntity<Void> deleteUser(@Valid UserId id) {
        service.deleteUser(id.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
