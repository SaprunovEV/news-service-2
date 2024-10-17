package com.example.newsservice2.news.service.adapter;

import com.example.newsservice2.news.model.NewsEntity;

import java.util.List;

public interface CategoryAdapter {
    List<Long> getCategoryByNews(NewsEntity news);
}
