package com.example.newsservice2.user.mapper;

import com.example.newsservice2.user.input.web.v1.model.UserListResponse;
import com.example.newsservice2.user.input.web.v1.model.UserResponse;
import com.example.newsservice2.user.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    default UserListResponse listEntitiesToResponseList(List<UserEntity> list) {
        List<UserResponse> userResponses = list.stream().map(this::entityToResponse).toList();
        return new UserListResponse(userResponses.size(), userResponses);
    }

    UserResponse entityToResponse(UserEntity userEntity);
}
