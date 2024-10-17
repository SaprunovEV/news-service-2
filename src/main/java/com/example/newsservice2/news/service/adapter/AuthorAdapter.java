package com.example.newsservice2.news.service.adapter;

import com.example.newsservice2.news.model.NewsEntity;
import com.example.newsservice2.news.service.model.Author;

public interface AuthorAdapter {
    Author getAuthorByNews(NewsEntity news);
}
