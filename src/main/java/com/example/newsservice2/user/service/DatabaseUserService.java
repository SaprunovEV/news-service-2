package com.example.newsservice2.user.service;

import com.example.newsservice2.user.input.web.v1.model.UserFilter;
import com.example.newsservice2.user.input.web.v1.model.UserPayload;
import com.example.newsservice2.user.model.UserEntity;
import com.example.newsservice2.user.output.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserEntity> findAll(UserFilter filter) {
        Page<UserEntity> page = repository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        return page.getContent();
    }

    @Override
    public UserEntity createUser(UserPayload payload) {
        UserEntity toSave = new UserEntity();
        toSave.setEmail(payload.getEmail());
        toSave.setNickName(payload.getNickName());
        return repository.save(toSave);
    }
}
