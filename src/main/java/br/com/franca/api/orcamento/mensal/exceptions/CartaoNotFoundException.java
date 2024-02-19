package br.com.franca.api.orcamento.mensal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartaoNotFoundException extends RuntimeException {

    public CartaoNotFoundException(String message) {
        super(message);
    }

    public CartaoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
