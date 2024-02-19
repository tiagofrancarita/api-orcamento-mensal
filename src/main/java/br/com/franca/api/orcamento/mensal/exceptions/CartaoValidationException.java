package br.com.franca.api.orcamento.mensal.exceptions;

public class CartaoValidationException extends RuntimeException{

    public CartaoValidationException() {
        super();
    }

    public CartaoValidationException(String message) {
        super(message);
    }

    public CartaoValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartaoValidationException(Throwable cause) {
        super(cause);
    }

}
