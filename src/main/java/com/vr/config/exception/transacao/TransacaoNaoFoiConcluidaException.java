package com.vr.config.exception.transacao;

public class TransacaoNaoFoiConcluidaException extends RuntimeException {
    public TransacaoNaoFoiConcluidaException(String message) {
        super(message);
    }
}
