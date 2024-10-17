package com.example.newsservice2.news.service;

import com.example.newsservice2.news.service.model.NewsManager;

import java.util.List;

public interface NewsService {
    List<NewsManager> getPageNews(int pageNumber, int pageSize);
}
