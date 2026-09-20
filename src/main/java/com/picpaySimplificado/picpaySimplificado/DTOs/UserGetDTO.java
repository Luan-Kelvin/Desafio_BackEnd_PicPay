package com.picpaySimplificado.picpaySimplificado.DTOs;

import com.picpaySimplificado.picpaySimplificado.Enum.UserType;

import java.math.BigDecimal;

public record UserGetDTO(
        Long id,
        String firsName,
        String lastName,
        BigDecimal balance,
        String email,
        UserType type
) {
}
