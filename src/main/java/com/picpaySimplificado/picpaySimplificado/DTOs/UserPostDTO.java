package com.picpaySimplificado.picpaySimplificado.DTOs;

import com.picpaySimplificado.picpaySimplificado.Enum.UserType;

import java.math.BigDecimal;

public record UserPostDTO(
        String firsName,
        String lastName,
        String document,
        String password,
        BigDecimal balance,
        String email,
        UserType type
) {
}
