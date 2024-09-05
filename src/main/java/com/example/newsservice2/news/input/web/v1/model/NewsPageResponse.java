package com.example.newsservice2.news.input.web.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsPageResponse {
    private long count;
    private List<NewsItem> news;
}
