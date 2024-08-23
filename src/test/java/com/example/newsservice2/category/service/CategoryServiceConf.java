package com.example.newsservice2.category.service;

import com.example.newsservice2.category.output.repository.specification.CategoryRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoryServiceConf {
    @Bean
    public CategoryService service(CategoryRepository repo) {
        return new DatabaseCategoryService(repo);
    }
}
