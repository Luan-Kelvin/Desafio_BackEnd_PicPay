package com.picpaySimplificado.picpaySimplificado.DTOs;

import java.time.LocalDateTime;

public record ErroRequest(
        LocalDateTime timestamp,
        Integer statusCode,
        String message,
        String requesrUri

) {
}
