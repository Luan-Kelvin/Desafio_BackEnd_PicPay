package com.picpaySimplificado.picpaySimplificado.DTOs;

import com.picpaySimplificado.picpaySimplificado.Enum.UserType;

public record UserGetDTO(
        Long id,
        String firsName,
        String lastName,
        String email,
        UserType type
) {
}
