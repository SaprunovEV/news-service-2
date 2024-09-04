package com.example.newsservice2.user.input.web.v1.annotation.openApi;

import com.example.newsservice2.user.input.web.v1.model.UserErrorResponse;
import com.example.newsservice2.user.input.web.v1.model.UserListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Get page of users.",
        description = "Get page of users. Return List users and count users.",
        tags = {"users, V1"},
        parameters = {@Parameter(name = "pageNumber", example = "2"), @Parameter(name = "pageSize", example = "5")},
        method = "GET"
)
@ApiResponse(
        responseCode = "200",
        content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserListResponse.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Validation error!",
        content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserErrorResponse.class))
)
public @interface GetPageUsersDock {
}
