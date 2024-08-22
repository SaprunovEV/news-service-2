package com.example.newsservice2.category.service;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;
import com.example.newsservice2.category.model.CategoryEntity;
import com.example.newsservice2.category.output.repository.specification.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCategoryService implements CategoryService {
    private final CategoryRepository repository;
    @Override
    public List<CategoryEntity> getByFilter(CategoryFilter filter) {
        Page<CategoryEntity> page = repository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        return page.getContent();
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
