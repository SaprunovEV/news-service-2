package com.example.newsservice2.user.service;

import com.example.newsservice2.user.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll(int pageNumber, int pageSize);

    UserEntity createUser(UserEntity payload);

    UserEntity updateUser(Long id, UserEntity payload);

    void deleteUser(Long id);
}
