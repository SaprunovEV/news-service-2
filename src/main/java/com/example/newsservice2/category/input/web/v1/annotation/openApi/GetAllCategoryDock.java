package com.example.newsservice2.category.input.web.v1.annotation.openApi;

import com.example.newsservice2.category.input.web.v1.model.CategoryErrorResponse;
import com.example.newsservice2.category.input.web.v1.model.CategoryListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@Operation(
        summary = "Get category with pagination.",
        tags = {"categories", "V1"},
        description = "Get category with pagination. Return list of categories.",
        parameters = {@Parameter(name = "pageSize"), @Parameter(name = "pageNumber")}
)
@ApiResponse(
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = CategoryListResponse.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Validation error",
        content = @Content(schema = @Schema(implementation = CategoryErrorResponse.class))
)
public @interface GetAllCategoryDock {
}
