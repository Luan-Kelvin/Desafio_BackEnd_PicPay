package com.picpaySimplificado.picpaySimplificado.Exceptions;

public class InsufficientFundsForTransactionException extends RuntimeException {
    public InsufficientFundsForTransactionException(String message) {
        super(message);
    }
}
