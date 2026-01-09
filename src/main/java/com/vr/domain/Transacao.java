package com.vr.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
@Data
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private Integer numeroCartao;

    private BigDecimal valor;

    private BigDecimal saldoAnterior;
}
