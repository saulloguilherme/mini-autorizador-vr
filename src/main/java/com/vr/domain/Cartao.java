package com.vr.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cartoes")
@Data
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private Integer numeroCartao;

    private Integer senha;

    private BigDecimal saldo;
}
