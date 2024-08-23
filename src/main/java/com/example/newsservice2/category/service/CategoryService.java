package com.example.newsservice2.category.service;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;
import com.example.newsservice2.category.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getByFilter(CategoryFilter filter);

    CategoryEntity createNewCategory(CategoryPayload payload);

    CategoryEntity updateCategory(Long id, CategoryPayload payload);

    void deleteCategory(Long id);
}
