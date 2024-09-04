package com.example.newsservice2.news.model;

import com.example.newsservice2.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "news")
@Getter
@Setter
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String body;
    private Instant createAt;
    private Instant updateAt;
    @OneToMany
    private List<CommentEntity> comment;
    @ManyToOne(targetEntity = UserEntity.class)
    private Long author;

    @PrePersist
    public void persist() {
        createAt = Instant.now();
        updateAt = Instant.now();
    }

    @PreUpdate
    public void update() {
        updateAt = Instant.now();
    }
}
