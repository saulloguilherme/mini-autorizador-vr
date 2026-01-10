package com.vr.service;

import com.vr.dto.TransacaoDTO;
import com.vr.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public Boolean realizarTransacao(TransacaoDTO transacaoDTO) {
        return true;
    }
}