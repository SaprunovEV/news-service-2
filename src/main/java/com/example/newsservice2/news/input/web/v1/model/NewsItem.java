package com.example.newsservice2.news.input.web.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsItem {
    private long id;
    private int count;
    private String topic;
    private String body;
    private long author;
    private Instant createAt;
    private Instant updateAt;
    private List<Long> categories;
}
