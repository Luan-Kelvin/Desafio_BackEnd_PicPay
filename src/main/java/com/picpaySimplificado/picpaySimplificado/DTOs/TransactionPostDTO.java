package com.picpaySimplificado.picpaySimplificado.DTOs;

import java.math.BigDecimal;

public record TransactionPostDTO(
        BigDecimal value,
        Long senderId,
        Long recieverId
) {
}
