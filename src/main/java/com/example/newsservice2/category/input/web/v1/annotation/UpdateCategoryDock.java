package com.example.newsservice2.category.input.web.v1.annotation;

import com.example.newsservice2.category.input.web.v1.model.CategoryErrorResponse;
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
        summary = "Update category.",
        tags = {"categories", "V1"},
        description = "Update category. Return updated category.",
        requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CategoryPayload.class)))
)
@ApiResponse(
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = CategoryPayload.class))
)
@ApiResponse(
        responseCode = "404",
        content = @Content(schema = @Schema(implementation = CategoryErrorResponse.class))
)
public @interface UpdateCategoryDock {
}
