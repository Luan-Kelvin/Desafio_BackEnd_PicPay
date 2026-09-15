package com.picpaySimplificado.picpaySimplificado.DTOs;

import java.time.LocalDateTime;

public record ErroRequest(
        LocalDateTime timesTamp,
        Integer statusCode,
        String message,
        String requesrUri

) {
}
