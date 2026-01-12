package com.vr.service;

import com.vr.config.exception.cartao.CartaoJaExisteException;
import com.vr.config.exception.cartao.CartaoNaoEncontradoException;
import com.vr.domain.Cartao;
import com.vr.dto.cartao.CartaoDTORequest;
import com.vr.dto.cartao.CartaoDTOResponse;
import com.vr.repository.CartaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;


class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @Autowired
    @InjectMocks
    private CartaoService cartaoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create and save a valid card")
    void criarCartaoSuccess() {

        CartaoDTORequest cartaoDTORequest = new CartaoDTORequest(2222, "2222222222");
        Cartao novoCartao = new Cartao(cartaoDTORequest);
        novoCartao.setSaldo(BigDecimal.valueOf(500));

        CartaoDTOResponse responseExample = new CartaoDTOResponse(novoCartao.getNumeroCartao());

        CartaoDTOResponse responseReal = cartaoService.criarCartao(cartaoDTORequest);

        assertThat(responseReal).isEqualTo(responseExample);
    }

    @Test
    @DisplayName("Should throw a exception when try to save a card that number already exist")
    void criarCartaoError() {

        CartaoDTORequest cartaoDTORequest = new CartaoDTORequest(2222, "2222222222");
        Cartao novoCartao = new Cartao(cartaoDTORequest);
        novoCartao.setSaldo(BigDecimal.valueOf(500));

        when(cartaoService.criarCartao(cartaoDTORequest)).thenThrow(new CartaoJaExisteException());


        CartaoDTOResponse responseExample = new CartaoDTOResponse(novoCartao.getNumeroCartao());

        Assertions.assertThrows(CartaoJaExisteException.class, () -> {
            cartaoService.criarCartao(cartaoDTORequest);
        });
    }

    @Test
    @DisplayName("Should get balance")
    void obterSaldoSuccess() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1233214");
        cartao.setSenha(2134);
        cartao.setSaldo(BigDecimal.valueOf(500));

        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(Optional.of(cartao));

        Cartao cartaoReal = cartaoService.obterCartao(cartao.getNumeroCartao());

        assertThat(cartaoReal.getSaldo()).isEqualTo(cartao.getSaldo());
    }

    @Test
    @DisplayName("Should not get balance")
    void obterSaldoError() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1233214");
        cartao.setSenha(2134);
        cartao.setSaldo(BigDecimal.valueOf(500));

        when(cartaoRepository.findByNumeroCartao(any())).thenReturn(Optional.empty());


        Assertions.assertThrows(CartaoNaoEncontradoException.class, () -> {
            cartaoService.obterCartao(cartao.getNumeroCartao());
        });
    }
}