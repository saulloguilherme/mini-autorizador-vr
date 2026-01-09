package com.vr.controller;

import com.vr.dto.TransacaoDTO;
import com.vr.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/transacoes")
public class TransacaoController {

    @Autowired
    TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        Boolean status = transacaoService.realizarTransacao(transacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

}
