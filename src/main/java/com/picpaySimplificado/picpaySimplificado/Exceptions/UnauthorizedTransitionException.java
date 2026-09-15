package com.picpaySimplificado.picpaySimplificado.Exceptions;

public class UnauthorizedTransitionException extends RuntimeException {
    public UnauthorizedTransitionException(String message) {
        super(message);
    }
}
