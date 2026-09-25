package com.picpaySimplificado.picpaySimplificado.DTOs;

import com.picpaySimplificado.picpaySimplificado.Enum.UserType;

import java.math.BigDecimal;

public record UserFindByDocumentDTO(
        Long id,
        String firstName,
        String lastName,
        String document,
        BigDecimal balance,
        String email,
        String password,
        UserType type
) {
}
