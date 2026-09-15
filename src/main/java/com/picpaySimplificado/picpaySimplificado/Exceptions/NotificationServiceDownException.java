package com.picpaySimplificado.picpaySimplificado.Exceptions;

public class NotificationServiceDownException extends RuntimeException {
    public NotificationServiceDownException(String message) {
        super(message);
    }
}
