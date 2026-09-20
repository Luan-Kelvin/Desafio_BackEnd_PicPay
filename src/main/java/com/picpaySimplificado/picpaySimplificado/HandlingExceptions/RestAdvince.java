package com.picpaySimplificado.picpaySimplificado.HandlingExceptions;

import com.picpaySimplificado.picpaySimplificado.DTOs.ErroRequest;
import com.picpaySimplificado.picpaySimplificado.Exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestAdvince {

    @ExceptionHandler(InsufficientFundsForTransactionException.class)
    public ResponseEntity<ErroRequest> insufficientFounds(InsufficientFundsForTransactionException e, HttpServletRequest request){
            ErroRequest erro = new ErroRequest(
                    LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(),
                    e.getMessage(),
                    request.getRequestURI()
            );

            return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(InvalidTransferAmountException.class)
    public ResponseEntity<ErroRequest> invalidTransfer(InvalidTransferAmountException e, HttpServletRequest request){
        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(InvalidUserTypeException.class)
    public ResponseEntity<ErroRequest> invalidUserType(InvalidUserTypeException e, HttpServletRequest request){
        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErroRequest> userExists(UserAlreadyExistsException e, HttpServletRequest request){
        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErroRequest> insufficientFounds(UserNotFoundException e, HttpServletRequest request){
        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(InvalidDepositoAmountException.class)
    public ResponseEntity<ErroRequest> invalidDeposit(InvalidDepositoAmountException e, HttpServletRequest request){
        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}
