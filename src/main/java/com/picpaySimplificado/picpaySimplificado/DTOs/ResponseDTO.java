package com.picpaySimplificado.picpaySimplificado.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDTO(
        @JsonAlias("status") String status
) {
}
