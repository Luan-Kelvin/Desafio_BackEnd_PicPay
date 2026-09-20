package com.picpaySimplificado.picpaySimplificado.Exceptions;

public class InvalidDepositoAmountException extends RuntimeException {
    public InvalidDepositoAmountException(String message) {
        super(message);
    }
}
