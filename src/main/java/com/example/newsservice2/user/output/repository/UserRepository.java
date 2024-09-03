package com.example.newsservice2.user.output.repository;

import com.example.newsservice2.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
