package com.example.newsservice2.category.service;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;
import com.example.newsservice2.category.model.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseCategoryService implements CategoryService {
    @Override
    public List<CategoryEntity> getByFilter(CategoryFilter filter) {
        return null;
    }

    @Override
    public CategoryEntity createNewCategory(CategoryPayload payload) {
        return null;
    }

    @Override
    public CategoryEntity updateCategory(Long id, CategoryPayload payload) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }
}
