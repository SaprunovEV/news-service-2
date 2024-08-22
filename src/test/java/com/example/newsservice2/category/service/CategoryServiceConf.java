package com.example.newsservice2.category.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoryServiceConf {
    @Bean
    public CategoryService service() {
        return new DatabaseCategoryService();
    }
}
