package com.picpaySimplificado.picpaySimplificado.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionGetDTO(
        Long id,
        Long idSender,
        String nameSender,
        Long idReciever,
        String nameReciever,
        BigDecimal value,
        LocalDateTime timesTamp
) {
}
