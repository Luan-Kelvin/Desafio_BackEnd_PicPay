package com.picpaySimplificado.picpaySimplificado.Services;

import com.picpaySimplificado.picpaySimplificado.DTOs.ResponseDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionDto;
import com.picpaySimplificado.picpaySimplificado.Domain.Transaction;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Exceptions.UnauthorizedTransitionException;
import com.picpaySimplificado.picpaySimplificado.Respository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;

    private final TransactionRepository transactionRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public void createTransaction(TransactionDto transaction) throws IOException, InterruptedException {
        User sender = userService.findById(transaction.senderId());
        User reciever = userService.findById(transaction.recieverId());

        userService.validateTransaction(sender, transaction.value());

        if (!autorizeTransaction()){
            throw new UnauthorizedTransitionException("ERRO! Transição não autorizada");
        }

        Transaction transaction1 = new Transaction(transaction.value(), sender, reciever);
        transactionRepository.save(transaction1);

        sender.senderTransfer(transaction.value());
        reciever.recieverTransfer(transaction.value());

        userService.saveUser(sender);
        userService.saveUser(reciever);

    }


    private boolean autorizeTransaction() throws IOException, InterruptedException {
        String endpoint = "https://util.devi.tools/api/v2/authorize";
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ResponseDTO dto = mapper.readValue(response.body(), ResponseDTO.class);

        if (dto.status().equalsIgnoreCase("success")){
            return true;
        }else {
            return false;
        }
    }

}
