package com.example.newsservice2.category.input.web.v1.annotation.openApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Delete category by id.",
        description = "Delete category by id. Return no content",
        tags = {"categories", "V1"},
        method = "DELETE"
)
@ApiResponse(
        responseCode = "204"
)
public @interface DeleteCategoryDock {
}
