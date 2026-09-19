package com.picpaySimplificado.picpaySimplificado.DTOs;

import com.picpaySimplificado.picpaySimplificado.Enum.UserType;

import java.math.BigDecimal;

public record UserPostDTO(
        String firstName,
        String lastName,
        String document,
        String password,
        BigDecimal balance,
        String email,
        UserType type
) {
}
