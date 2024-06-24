package com.curriculo.desafio.config.exceptions;

public class VotoException extends RuntimeException {

    public VotoException() {
        super();
    }

    public VotoException(String message) {
        super(message);
    }

    public VotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public VotoException(Throwable cause) {
        super(cause);
    }
}