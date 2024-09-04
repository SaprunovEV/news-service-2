package com.example.newsservice2.news.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "news_category")
@Getter
@Setter
public class News2CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = NewsEntity.class)
    private Long news;
    @ManyToOne(targetEntity = CommentEntity.class)
    private Long category;
}
