package com.example.newsservice2.category.input.web.v1.controller;

import com.example.newsservice2.category.input.web.v1.annotation.CreateCategoryDock;
import com.example.newsservice2.category.input.web.v1.annotation.openApi.GetAllCategoryDock;
import com.example.newsservice2.category.input.web.v1.model.*;
import com.example.newsservice2.category.mapper.CategoryMapper;
import com.example.newsservice2.category.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Categories V1", description = "Categories API version V1")
@Slf4j
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService service;
    private final CategoryMapper mapper;
    @GetMapping()
    @GetAllCategoryDock
    public ResponseEntity<CategoryListResponse> findAllCategory(@Valid CategoryFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.entityListToResponseList(service.getByFilter(filter))
        );
    }

    @PostMapping
    @CreateCategoryDock
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryPayload payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.entityToResponse(service.createNewCategory(payload))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @Valid CategoryId id,
            @RequestBody @Valid CategoryPayload payload) {
        return ResponseEntity.ok(mapper.entityToResponse(service.updateCategory(id.getId(), payload)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@Valid CategoryId id) {
        service.deleteCategory(id.getId());
        return ResponseEntity.noContent().build();
    }

}
