package com.vr.dto;

import java.math.BigDecimal;

public record TransacaoDTO(String numeroCartao, Integer senhaCartao, BigDecimal valor) {
}
