package com.picpaySimplificado.picpaySimplificado.Service.Transaction;

import com.picpaySimplificado.picpaySimplificado.Conversor.Conversor;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionPostDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.Transaction;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Respository.TransactionRepository;
import com.picpaySimplificado.picpaySimplificado.Services.TransactionService;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Conversor conversor;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Deve criar nova transação, salvar os dois usuarios com os saldos novos  e retornar DTO")
    void deveCrirTransacaoERetornarDTO(){
        User sender = new User(
                "Joaquim",
                "Felipe",
                "123.456.789-10",
                "12345",
                BigDecimal.valueOf(800),
                "Jojo@gmail.com",
                UserType.COMMON
        );

        User recieve = new User(
                "Márcio",
                "Manoel",
                "325.555.987-10",
                "25456",
                BigDecimal.valueOf(600),
                "Mama@gmail.com",
                UserType.COMMON
        );

        TransactionGetDTO getDTO = new TransactionGetDTO(
                1L,
                sender.getId(),
                sender.getFirstName(),
                recieve.getId(),
                recieve.getFirstName(),
                BigDecimal.valueOf(300),
                LocalDateTime.now()
        );

        TransactionPostDTO postDto = new TransactionPostDTO(BigDecimal.valueOf(300), 1L, 2L);

        when(userService.findById(eq(postDto.senderId()))).thenReturn(sender);
        when(userService.findById(eq(postDto.recieverId()))).thenReturn(recieve);
        when(conversor.converterTransaction(any(Transaction.class))).thenReturn(getDTO);
        doNothing().when(userService).validateTransaction(any(User.class), eq(postDto.value()));
        doNothing().when(userService).saveUser(sender);
        doNothing().when(userService).saveUser(recieve);

        TransactionGetDTO transactionGetDTO =  transactionService.createTransaction(postDto);

        ArgumentCaptor<Transaction> captorTransaction = ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository).save(captorTransaction.capture());

        Transaction transactionSave = captorTransaction.getValue();

        assertEquals(postDto.value(), transactionSave.getAmount());
        assertEquals(transactionSave.getSender().getId(), transactionGetDTO.idSender());
        assertEquals(transactionSave.getReciever().getId(), transactionGetDTO.idReciever());
        assertEquals(transactionSave.getAmount(), transactionGetDTO.value());

        verify(userService).findById(eq(postDto.senderId()));
        verify(userService).findById(eq(postDto.recieverId()));
        verify(userService).validateTransaction(any(User.class), eq(postDto.value()));
        verify(userService).saveUser(sender);
        verify(userService).saveUser(recieve);
        verify(conversor).converterTransaction(any(Transaction.class));
    }

    @Test
    @DisplayName("Deve retornar lista com todoas as transições encontradas")
    void deveRetornarListaCOmtransacoes(){
        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();
        List<Transaction> transactions = List.of(t1, t2);

        when(transactionRepository.findAll()).thenReturn(transactions);

        List<TransactionGetDTO> listDto = transactionService.listTransactions();

        assertEquals(2, listDto.size());

        verify(transactionRepository).findAll();

    }


}
