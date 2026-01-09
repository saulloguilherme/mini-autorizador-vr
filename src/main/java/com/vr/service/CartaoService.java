package com.vr.service;

import com.vr.dto.cartao.CartaoDTORequest;
import com.vr.dto.cartao.CartaoDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {

    @Autowired
    private CartaoService cartaoService;

    public CartaoDTOResponse criarCartao(CartaoDTORequest cartaoDTORequest) {
        return null;
    }

    public BigDecimal obterSaldo(Integer numeroCartao) {
        return BigDecimal.TEN;
    }

}
