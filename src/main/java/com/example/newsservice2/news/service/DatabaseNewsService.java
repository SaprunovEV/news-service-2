package com.example.newsservice2.news.service;

import com.example.newsservice2.news.model.NewsEntity;
import com.example.newsservice2.news.output.repository.NewsRepository;
import com.example.newsservice2.news.service.adapter.AuthorAdapter;
import com.example.newsservice2.news.service.adapter.CategoryAdapter;
import com.example.newsservice2.news.service.adapter.CommentAdapter;
import com.example.newsservice2.news.service.model.NewsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseNewsService implements NewsService {
    private final NewsRepository repository;
    private final AuthorAdapter authorAdapter;
    private final CategoryAdapter categoryAdapter;
    private final CommentAdapter commentAdapter;

    @Override
    public List<NewsManager> getPageNews(int pageNumber, int pageSize) {
        Page<NewsEntity> page = repository.findAll(PageRequest.of(pageNumber, pageSize));

        return page.map(news -> NewsManager.builder()
                .author(authorAdapter.getAuthorByNews(news))
                .categories(categoryAdapter.getCategoryByNews(news))
                .count(commentAdapter.getCountByNews(news))
                .news(news)
                .build()).getContent();
    }
}
