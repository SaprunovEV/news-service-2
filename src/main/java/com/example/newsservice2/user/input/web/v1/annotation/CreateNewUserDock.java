package com.example.newsservice2.user.input.web.v1.annotation;

import com.example.newsservice2.user.input.web.v1.model.UserErrorResponse;
import com.example.newsservice2.user.input.web.v1.model.UserPayload;
import com.example.newsservice2.user.input.web.v1.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Create new User.",
        description = "Create new User. Return new user.",
        tags = {"users, V1"},
        method = "POST",
        requestBody = @RequestBody(content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserPayload.class)))
)
@ApiResponse(
        responseCode = "201",
        content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserResponse.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Validation error!",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserErrorResponse.class))
)
public @interface CreateNewUserDock {
}
