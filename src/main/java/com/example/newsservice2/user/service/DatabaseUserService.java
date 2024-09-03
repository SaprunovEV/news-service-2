package com.example.newsservice2.user.service;

import com.example.newsservice2.user.input.web.v1.model.UserFilter;
import com.example.newsservice2.user.input.web.v1.model.UserPayload;
import com.example.newsservice2.user.model.UserEntity;
import com.example.newsservice2.user.output.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

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

    @Override
    public UserEntity updateUser(Long id, UserEntity payload) {
        Optional<UserEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new UserNotFoundException(MessageFormat.format("Пользователь с id: {0} не найден", id));
        }
        UserEntity user = optional.get();
        user.update(payload);
        return repository.save(user);
    }
}
