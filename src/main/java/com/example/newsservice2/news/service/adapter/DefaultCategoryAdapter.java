package com.example.newsservice2.news.service.adapter;

import com.example.newsservice2.news.model.NewsEntity;

import java.util.List;

@Deprecated
public class DefaultCategoryAdapter implements CategoryAdapter {
    @Override
    public List<Long> getCategoryByNews(NewsEntity news) {
        return null;
    }
}
