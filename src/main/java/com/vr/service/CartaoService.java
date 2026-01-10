package com.vr.service;

import com.vr.config.exception.cartao.CartaoJaExisteException;
import com.vr.config.exception.cartao.CartaoNaoEncontradoException;
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

    public Cartao obterCartao(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
    }

    public BigDecimal obterSaldo(String numeroCartao) {
        return obterCartao(numeroCartao).getSaldo();
    }

    public Boolean validarSenha(Cartao cartao, Integer senha) {
        return cartao.getSenha().equals(senha);
    }

    public Boolean validarSaldo(Cartao cartao, BigDecimal valor) {
        return cartao.getSaldo().compareTo(valor) >= 0;
    }

    public void salvarCartao(Cartao cartao) {
        cartaoRepository.save(cartao);
    }
}