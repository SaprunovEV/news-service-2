package com.example.newsservice2.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usr")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickName;

    public void update(UserEntity payload) {
        this.email = payload.email;
        this.nickName = payload.nickName;
    }
}
