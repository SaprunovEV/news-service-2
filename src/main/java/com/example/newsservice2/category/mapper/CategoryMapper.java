package com.example.newsservice2.category.mapper;

import com.example.newsservice2.category.input.web.v1.model.CategoryListResponse;
import com.example.newsservice2.category.input.web.v1.model.CategoryResponse;
import com.example.newsservice2.category.model.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    default CategoryListResponse entityListToResponseList(List<CategoryEntity> entities) {
        List<CategoryResponse> categoryResponses = entities.stream().map(this::entityToResponse).toList();
        return new CategoryListResponse(categoryResponses);
    }

    CategoryResponse entityToResponse(CategoryEntity categoryEntity);
}
