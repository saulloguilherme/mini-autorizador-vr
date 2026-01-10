package com.vr.config.exception;

import com.vr.dto.cartao.CartaoDTOResponse;
import lombok.Getter;

@Getter
public class CartaoJaExisteException extends RuntimeException {

    private CartaoDTOResponse cartao;

    public CartaoJaExisteException() {
        super();
    }
    public CartaoJaExisteException(CartaoDTOResponse cartao) {
        super();
        this.cartao = cartao;
    }
}
