package com.example.newsservice2.user.input.web.v1.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPayload {
    @NotBlank(message = "Имя пользователя не должно быть пустое!")
    @NotNull(message = "Имя пользователя не должно быть null!")
    private String nickName;
    @NotBlank(message = "Имя пользователя не должно быть пустое!")
    @Pattern(regexp = "^[^\\s@]+@([^\\s@.,]+\\.)+[^\\s@.,]{2,}$",
    message = "Неверная почта!")
    private String email;
}
