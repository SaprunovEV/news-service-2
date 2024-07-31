package com.example.newsservice2.category.input.web.v1.model;

import lombok.Data;

import java.util.List;
@Data
public class CategoryListResponse {

    private final int count;
    private final List<CategoryResponse> categoryResponses;

    public CategoryListResponse(List<CategoryResponse> categoryResponses) {

        this.categoryResponses = categoryResponses;
        this.count = categoryResponses.size();
    }
}
