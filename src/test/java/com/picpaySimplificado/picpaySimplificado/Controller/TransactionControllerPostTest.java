package com.picpaySimplificado.picpaySimplificado.Controller;

import com.picpaySimplificado.picpaySimplificado.Controllers.TransactionController.Post.TransactionControllerPost;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionPostDTO;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InvalidUserTypeException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.UserNotFoundException;
import com.picpaySimplificado.picpaySimplificado.Services.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionControllerPost.class)
public class TransactionControllerPostTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    @DisplayName("Deve retornar status 201 - CREATED quando for criado nova transação.")
    void deveRetornar201QuandoForCriadaNovaTransacao() throws Exception {
        String json = """
                {
                    "value": 200,
                    "senderId": 1,
                    "recieverId": 2
                }
                """;

        TransactionGetDTO getDto = new TransactionGetDTO(
                1L,
                1L,
                "Joaquim",
                2L,
                "Pedro",
                BigDecimal.valueOf(200),
                LocalDateTime.now()
        );

        when(transactionService.createTransaction(any(TransactionPostDTO.class))).thenReturn(getDto);

        mvc.perform(
                post("http://localhost:8080/transactions")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());

        verify(transactionService).createTransaction(any(TransactionPostDTO.class));
    }

    @Test
    @DisplayName("Deve retornar Status 404 - NOT FOUND, se um dos usuários passado no json não existir.")
    void deveRetornar404SeUsuarioNaoExistir() throws Exception {
        String json = """
                {
                    "value": 200,
                    "senderId": 1,
                    "recieverId": 2
                }
                """;

        doThrow(new UserNotFoundException("ERRO! Usuário não encontrado"))
                .when(transactionService).createTransaction(any(TransactionPostDTO.class));

        mvc.perform(
                post("http://localhost:8080/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());

        verify(transactionService).createTransaction(any(TransactionPostDTO.class));
    }

    @Test
    @DisplayName("Deve retornar 409 - CONFLICT se tipo de usuário for inválido.")
    void deveRetornar409SeTipoForInvalido() throws Exception {
        String json = """
                {
                    "value": 200,
                    "senderId": 1,
                    "recieverId": 2
                }
                """;

        doThrow(new InvalidUserTypeException("ERRO! Type inválido"))
                .when(transactionService).createTransaction(any(TransactionPostDTO.class));

        mvc.perform(
                post("http://localhost/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isConflict());

        verify(transactionService).createTransaction(any(TransactionPostDTO.class));
    }


}
