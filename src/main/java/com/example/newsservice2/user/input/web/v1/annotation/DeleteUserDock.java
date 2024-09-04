package com.example.newsservice2.user.input.web.v1.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Delete user by id.",
        description = "Delete user by id. Return no content.",
        tags = {"users", "V1"},
        method = "DELETE",
        parameters = @Parameter(name = "id", example = "1")
)
@ApiResponse(
        responseCode = "204"
)
public @interface DeleteUserDock {
}
