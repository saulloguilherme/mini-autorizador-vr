package com.vr.service;

import com.vr.config.exception.cartao.CartaoNaoEncontradoException;
import com.vr.config.exception.transacao.ErroTransacao;
import com.vr.config.exception.transacao.TransacaoNaoFoiConcluidaException;
import com.vr.domain.Cartao;
import com.vr.domain.Transacao;
import com.vr.dto.TransacaoDTO;
import com.vr.repository.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @Mock
    private CartaoService cartaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Autowired
    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should do Transacao successfully")
    void realizarTransacao() {
        Cartao cartaoExample = new Cartao();
        cartaoExample.setNumeroCartao("1111");
        cartaoExample.setSenha(1111);
        cartaoExample.setSaldo(BigDecimal.valueOf(500));

        TransacaoDTO transacaoDTO = new TransacaoDTO(cartaoExample.getNumeroCartao(), cartaoExample.getSenha(), BigDecimal.valueOf(499));

        when(cartaoService.obterCartao(cartaoExample.getNumeroCartao())).thenReturn(cartaoExample);
        when(cartaoService.validarSenha(any(), any())).thenReturn(Boolean.TRUE);
        when(cartaoService.validarSaldo(any(), any())).thenReturn(Boolean.TRUE);


        transacaoService.realizarTransacao(transacaoDTO);

        verify(transacaoRepository, times(1)).save(any());

        cartaoExample.setSaldo(BigDecimal.valueOf(1));
        verify(cartaoService, times(1)).salvarCartao(cartaoExample);
    }

    @Test
    @DisplayName("Should throw Exception when password is wrong")
    void realizarTransacaoErrorPassword() {
        Cartao cartaoExample = new Cartao();
        cartaoExample.setNumeroCartao("1111");
        cartaoExample.setSenha(1111);
        cartaoExample.setSaldo(BigDecimal.valueOf(500));

        TransacaoDTO transacaoDTO = new TransacaoDTO(cartaoExample.getNumeroCartao(), cartaoExample.getSenha(), BigDecimal.valueOf(499));

        when(cartaoService.obterCartao(cartaoExample.getNumeroCartao())).thenReturn(cartaoExample);
        when(cartaoService.validarSenha(any(), any())).thenReturn(Boolean.FALSE);
        when(cartaoService.validarSaldo(any(), any())).thenReturn(Boolean.TRUE);


        Exception thrown = Assertions.assertThrows(TransacaoNaoFoiConcluidaException.class, () -> {
            transacaoService.realizarTransacao(transacaoDTO);
        });

        Assertions.assertEquals(ErroTransacao.SENHA_INVALIDA.toString(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw Exception when account don't exist")
    void realizarTransacaoErrorAccount() {
        Cartao cartaoExample = new Cartao();
        cartaoExample.setNumeroCartao("1111");
        cartaoExample.setSenha(1111);
        cartaoExample.setSaldo(BigDecimal.valueOf(500));

        TransacaoDTO transacaoDTO = new TransacaoDTO(cartaoExample.getNumeroCartao(), cartaoExample.getSenha(), BigDecimal.valueOf(499));

        when(cartaoService.obterCartao(cartaoExample.getNumeroCartao())).thenThrow(new CartaoNaoEncontradoException());
        when(cartaoService.validarSenha(any(), any())).thenReturn(Boolean.TRUE);
        when(cartaoService.validarSaldo(any(), any())).thenReturn(Boolean.TRUE);


        Exception thrown = Assertions.assertThrows(TransacaoNaoFoiConcluidaException.class, () -> {
            transacaoService.realizarTransacao(transacaoDTO);
        });

        Assertions.assertEquals(ErroTransacao.CARTAO_INEXISTENTE.toString(), thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw Exception when transaction value is bigger than actual balance")
    void realizarTransacaoErrorValue() {
        Cartao cartaoExample = new Cartao();
        cartaoExample.setNumeroCartao("1111");
        cartaoExample.setSenha(1111);
        cartaoExample.setSaldo(BigDecimal.valueOf(500));

        TransacaoDTO transacaoDTO = new TransacaoDTO(cartaoExample.getNumeroCartao(), cartaoExample.getSenha(), BigDecimal.valueOf(499));

        when(cartaoService.obterCartao(cartaoExample.getNumeroCartao())).thenReturn(cartaoExample);
        when(cartaoService.validarSenha(any(), any())).thenReturn(Boolean.TRUE);
        when(cartaoService.validarSaldo(any(), any())).thenReturn(Boolean.FALSE);


        Exception thrown = Assertions.assertThrows(TransacaoNaoFoiConcluidaException.class, () -> {
            transacaoService.realizarTransacao(transacaoDTO);
        });

        Assertions.assertEquals(ErroTransacao.SALDO_INSUFICIENTE.toString(), thrown.getMessage());
    }

}