package com.example.newsservice2.news.service.adapter;

import com.example.newsservice2.news.model.NewsEntity;

public interface CommentAdapter {
    Long getCountByNews(NewsEntity news);
}
