package com.example.newsservice2.user.input.web.v1.annotation.openApi;

import com.example.newsservice2.user.input.web.v1.model.UserErrorResponse;
import com.example.newsservice2.user.input.web.v1.model.UserPayload;
import com.example.newsservice2.user.input.web.v1.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Update user.",
        description = "Update user. Return updated user.",
        tags = {"users", "V1"},
        method = "PUT",
        parameters = @Parameter(name = "id", example = "1"),
        requestBody = @RequestBody(content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserPayload.class)))
)
@ApiResponse(
        responseCode = "200",
        content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserResponse.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Validation error!",
        content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserErrorResponse.class))
)
@ApiResponse(
        responseCode = "404",
        description = "User not found",
        content = @Content(mediaType = "Application/json", schema = @Schema(implementation = UserErrorResponse.class))
)
public @interface UpdateUserDock {
}
