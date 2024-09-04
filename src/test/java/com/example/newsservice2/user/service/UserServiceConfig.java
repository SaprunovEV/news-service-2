package com.example.newsservice2.user.service;

import com.example.newsservice2.user.output.repository.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UserServiceConfig {

    @Bean
    public UserService service(UserRepository userRepository) {
        return new DatabaseUserService(userRepository);
    }
}
