package com.curriculo.desafio.config.exceptions;

public class PautaException extends RuntimeException {

    public PautaException() {
        super();
    }

    public PautaException(String message) {
        super(message);
    }

    public PautaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PautaException(Throwable cause) {
        super(cause);
    }
}