package com.picpaySimplificado.picpaySimplificado.DTOs;

import java.math.BigDecimal;

public record DepositDTO(
        String document,
        BigDecimal value
) {
}
