package com.vr.dto;

import java.math.BigDecimal;

public record TransacaoDTO(Integer numeroCartao, Integer senhaCartao, BigDecimal valor) {
}
