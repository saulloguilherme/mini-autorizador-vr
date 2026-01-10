package com.vr.controller;

import com.vr.dto.cartao.CartaoDTORequest;
import com.vr.dto.cartao.CartaoDTOResponse;
import com.vr.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoService cartaoService;

    @PostMapping()
    public ResponseEntity<CartaoDTOResponse> criarCartao(@RequestBody CartaoDTORequest cartaoDTORequest) {
        CartaoDTOResponse cartaoCriado = cartaoService.criarCartao(cartaoDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoCriado);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
        BigDecimal saldo = cartaoService.obterSaldo(numeroCartao);
        return ResponseEntity.status(HttpStatus.OK).body(saldo);
    }
}