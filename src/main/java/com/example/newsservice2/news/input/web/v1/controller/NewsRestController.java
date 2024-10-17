package com.example.newsservice2.news.input.web.v1.controller;

import com.example.newsservice2.news.input.web.v1.mapper.NewsMapper;
import com.example.newsservice2.news.input.web.v1.model.NewsFilter;
import com.example.newsservice2.news.input.web.v1.model.NewsPageResponse;
import com.example.newsservice2.news.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsRestController {
    private final NewsService service;
    private final NewsMapper mapper;

    @GetMapping
    public ResponseEntity<NewsPageResponse> getPage(@Valid NewsFilter filter) {
        return ResponseEntity.ok(
                mapper.listEntityToPageResponse(
                        service.getPageNews(filter.getPageNumber(), filter.getPageSize())));
    }
}
