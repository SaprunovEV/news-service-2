package com.example.newsservice2.news.service;

import com.example.newsservice2.news.output.repository.NewsRepository;
import com.example.newsservice2.news.service.adapter.AuthorAdapter;
import com.example.newsservice2.news.service.adapter.CategoryAdapter;
import com.example.newsservice2.news.service.adapter.CommentAdapter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class NewsServiceConf {
    @MockBean
    private AuthorAdapter authorAdapter;
    @MockBean
    private CommentAdapter commentAdapter;
    @MockBean
    private CategoryAdapter categoryAdapter;

    @Bean
    public NewsService service(NewsRepository newsRepository, AuthorAdapter author, CategoryAdapter category, CommentAdapter comment) {
        return new DatabaseNewsService(newsRepository, author, category, comment);
    }
}
