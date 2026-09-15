package com.picpaySimplificado.picpaySimplificado.DTOs;

import java.math.BigDecimal;

public record TransactionDto(
        BigDecimal value,
        Long senderId,
        Long recieverId
) {
}
