package com.picpaySimplificado.picpaySimplificado.Conversor;

import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.Transaction;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import org.springframework.stereotype.Service;

@Service
public class Conversor {

    public UserGetDTO converterUser(User user){
        return new UserGetDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBalance(),
                user.getEmail(),
                user.getUserType()
        );
    }

    public TransactionGetDTO converterTransaction(Transaction transaction){
        return new TransactionGetDTO(
                transaction.getId(),
                transaction.getSender().getId(),
                transaction.getSender().getFirstName(),
                transaction.getReceiver().getId(),
                transaction.getReceiver().getFirstName(),
                transaction.getAmount(),
                transaction.getTimestamp()
        );
    }
}
