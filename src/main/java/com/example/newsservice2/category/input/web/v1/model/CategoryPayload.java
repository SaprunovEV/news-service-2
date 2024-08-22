package com.example.newsservice2.category.input.web.v1.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPayload {
    @NotBlank(message = "Имя категории не должно быть пустое!")
    @NotNull(message = "Имя категории не должно быть null!")
    @Min(value = 5, message = "Имя категории должно быть от 5 символов!")
    private String name;
}
