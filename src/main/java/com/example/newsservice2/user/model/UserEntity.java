package com.example.newsservice2.user.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usr")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
