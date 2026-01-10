package com.vr.domain;

import com.vr.dto.cartao.CartaoDTORequest;
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

    @Column(unique = true)
    private String numeroCartao;

    private Integer senha;

    private BigDecimal saldo;

    public Cartao() {}
    
    public Cartao(CartaoDTORequest cartaoDTORequest) {
        setNumeroCartao(cartaoDTORequest.numeroCartao());
        setSenha(cartaoDTORequest.senha());
    }
}