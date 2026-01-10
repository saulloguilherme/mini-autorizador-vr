package com.vr.service;

import com.vr.config.exception.CartaoJaExisteException;
import com.vr.config.exception.CartaoNaoEncontradoException;
import com.vr.domain.Cartao;
import com.vr.dto.cartao.CartaoDTORequest;
import com.vr.dto.cartao.CartaoDTOResponse;
import com.vr.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public CartaoDTOResponse criarCartao(CartaoDTORequest cartaoDTORequest) {
        Cartao novoCartao = new Cartao(cartaoDTORequest);
        novoCartao.setSaldo(BigDecimal.valueOf(500));

        CartaoDTOResponse response = new CartaoDTOResponse(novoCartao.getNumeroCartao());

        try {
            cartaoRepository.save(novoCartao);
        } catch (DataIntegrityViolationException e) {
            throw new CartaoJaExisteException(response);
        }

        return response;
    }

    public BigDecimal obterSaldo(String numeroCartao) {
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
        return cartao.getSaldo();
    }

}
