package com.vr.config.exception;

import com.vr.config.exception.cartao.CartaoJaExisteException;
import com.vr.config.exception.cartao.CartaoNaoEncontradoException;
import com.vr.config.exception.transacao.TransacaoNaoFoiConcluidaException;
import com.vr.dto.cartao.CartaoDTOResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartaoJaExisteException.class)
    public ResponseEntity<CartaoDTOResponse> handleCartaoJaExistente(CartaoJaExisteException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(exception.getCartao());
    }

    @ExceptionHandler(CartaoNaoEncontradoException.class)
    public ResponseEntity<Object> handleCartaoNaoEncontradoException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(TransacaoNaoFoiConcluidaException.class)
    public ResponseEntity<String> handleTransacaoNaoConcluida(TransacaoNaoFoiConcluidaException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(exception.getMessage());
    }
}