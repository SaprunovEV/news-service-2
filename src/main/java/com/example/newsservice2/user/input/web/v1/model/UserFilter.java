package com.example.newsservice2.user.input.web.v1.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {
    @PositiveOrZero(message = "Номер страницы должен быть положителен!")
    private int pageNumber;
    @Positive(message = "Размер страницы должен быть положителен!")
    private int pageSize;
}
