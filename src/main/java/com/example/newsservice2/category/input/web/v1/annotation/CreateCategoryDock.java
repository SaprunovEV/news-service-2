package com.example.newsservice2.category.input.web.v1.annotation;

import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Create new category.",
        tags = {"categories", "V1"},
        description = "Create new category. Return new category.",
        requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CategoryPayload.class)))
)
@ApiResponse(
        responseCode = "201",
        content = @Content(schema = @Schema(implementation = CategoryPayload.class))
)
public @interface CreateCategoryDock {
}
