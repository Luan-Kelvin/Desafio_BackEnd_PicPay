package com.picpaySimplificado.picpaySimplificado.Services;

import com.picpaySimplificado.picpaySimplificado.Conversor.Conversor;
import com.picpaySimplificado.picpaySimplificado.DTOs.ResponseDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionPostDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.Transaction;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Respository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;

    private final TransactionRepository transactionRepository;

    private final Conversor conversor;

    public TransactionGetDTO createTransaction(TransactionPostDTO transaction){
        User sender = userService.findById(transaction.senderId());
        User reciever = userService.findById(transaction.recieverId());

        userService.validateTransaction(sender, transaction.value());

        Transaction transaction1 = new Transaction(transaction.value(), sender, reciever);
        transactionRepository.save(transaction1);

        sender.senderTransfer(transaction.value());
        reciever.recieverTransfer(transaction.value());

        userService.saveUser(sender);
        userService.saveUser(reciever);

        return conversor.converterTransaction(transaction1);

    }

    public List<TransactionGetDTO> listTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream().map(conversor::converterTransaction).toList();
    }
}
