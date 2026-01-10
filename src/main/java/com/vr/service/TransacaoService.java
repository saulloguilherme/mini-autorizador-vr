package com.vr.service;

import com.vr.config.exception.cartao.CartaoNaoEncontradoException;
import com.vr.config.exception.transacao.ErroTransacao;
import com.vr.config.exception.transacao.TransacaoNaoFoiConcluidaException;
import com.vr.domain.Cartao;
import com.vr.domain.Transacao;
import com.vr.dto.TransacaoDTO;
import com.vr.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private TransacaoRepository transacaoRepository;


    public void realizarTransacao(TransacaoDTO transacaoDTO) {
        try {
            Cartao cartao = cartaoService.obterCartao(transacaoDTO.numeroCartao());
            if (!cartaoService.validarSenha(cartao, transacaoDTO.senhaCartao())) {
                throw new TransacaoNaoFoiConcluidaException(ErroTransacao.SENHA_INVALIDA.toString());
            }
            if (!cartaoService.validarSaldo(cartao, transacaoDTO.valor())) {
                throw new TransacaoNaoFoiConcluidaException(ErroTransacao.SALDO_INSUFICIENTE.toString());
            }

            cartao.subtrairSaldo(transacaoDTO.valor());
            cartaoService.salvarCartao(cartao);

        } catch (CartaoNaoEncontradoException e) {
            throw new TransacaoNaoFoiConcluidaException(ErroTransacao.CARTAO_INEXISTENTE.toString());
        }

        Transacao transacao = new Transacao(transacaoDTO);

        transacaoRepository.save(transacao);
    }
}