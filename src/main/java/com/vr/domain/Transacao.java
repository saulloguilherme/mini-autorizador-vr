package com.vr.domain;

import com.vr.dto.TransacaoDTO;
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

    private String numeroCartao;

    private BigDecimal valor;

    public Transacao(TransacaoDTO transacaoDTO) {
        this.setValor(transacaoDTO.valor());
        this.setNumeroCartao(transacaoDTO.numeroCartao());
    }

    public Transacao() {}
}