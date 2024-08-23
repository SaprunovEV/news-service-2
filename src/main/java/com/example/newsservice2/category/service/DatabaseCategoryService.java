package com.example.newsservice2.category.service;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;
import com.example.newsservice2.category.model.CategoryEntity;
import com.example.newsservice2.category.output.repository.specification.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

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
        CategoryEntity result = new CategoryEntity();
        result.setName(payload.getName());
        return repository.save(result);
    }

    @Override
    public CategoryEntity updateCategory(Long id, CategoryPayload payload) {
        Optional<CategoryEntity> optional = repository.findById(id);

        CategoryEntity newEntity = new CategoryEntity();
        newEntity.setName(payload.getName());

        if (optional.isEmpty()) {
            throw new CategoryEntityNotFound(MessageFormat.format("Категория с id: {0} не найдена!", id));
        }
        CategoryEntity oldEntity = optional.get();

        oldEntity.update(newEntity);

        repository.save(oldEntity);
        return oldEntity;
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
