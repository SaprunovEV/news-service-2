package com.example.newsservice2.category.input.web.v1.controller;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.category.input.web.v1.model.CategoryListResponse;
import com.example.newsservice2.category.mapper.CategoryMapper;
import com.example.newsservice2.category.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/controllers")
@Tag(name = "Categories V1", description = "Categories API version V1")
@Slf4j
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService service;
    private final CategoryMapper mapper;
    @GetMapping()
    public ResponseEntity<CategoryListResponse> findAllCategory(@RequestParam CategoryFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.entityListToResponseList(service.getByFilter(filter))
        );
    }
}
