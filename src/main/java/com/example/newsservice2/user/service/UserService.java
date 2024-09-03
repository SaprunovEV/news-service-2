package com.example.newsservice2.user.service;

import com.example.newsservice2.user.input.web.v1.model.UserFilter;
import com.example.newsservice2.user.input.web.v1.model.UserPayload;
import com.example.newsservice2.user.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll(UserFilter filter);

    UserEntity createUser(UserPayload payload);
}
