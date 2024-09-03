package com.example.newsservice2.user.input.web.v1.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId {
    @NotNull(message = "Id не должен быть null!")
    @Positive(message = "Id должен быть положителен!")
    private Long id;
}
