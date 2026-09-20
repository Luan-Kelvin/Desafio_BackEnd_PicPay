package com.picpaySimplificado.picpaySimplificado.DTOs;

import com.picpaySimplificado.picpaySimplificado.Enum.UserType;

public record UserPostDTO(
        String firstName,
        String lastName,
        String document,
        String password,
        String email,
        UserType type
) {
}
