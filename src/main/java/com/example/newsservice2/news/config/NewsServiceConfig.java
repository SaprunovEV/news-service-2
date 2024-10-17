package com.example.newsservice2.news.config;

import com.example.newsservice2.news.service.adapter.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsServiceConfig {

    @Bean
    @ConditionalOnMissingBean
    public AuthorAdapter authorAdapter() {
        return new DefaultAuthorAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public CategoryAdapter categoryAdapter() {
        return new DefaultCategoryAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommentAdapter commentAdapter() {
        return new DefaultCommentAdapter();
    }
}
