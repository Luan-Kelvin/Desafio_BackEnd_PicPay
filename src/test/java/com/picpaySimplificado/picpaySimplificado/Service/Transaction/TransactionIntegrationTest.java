package com.picpaySimplificado.picpaySimplificado.Service.Transaction;

import com.picpaySimplificado.picpaySimplificado.Conversor.Conversor;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.Transaction;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Respository.TransactionRepository;
import com.picpaySimplificado.picpaySimplificado.Respository.UserRepository;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionIntegrationTest {

    @Autowired
    private  UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private Conversor conversor;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Deve criar nova transação e salvar os dois usuarios com seus novos saldos.")
    void deveCriatTransacao() throws Exception {
        User sender = new User(
                "Joaquim",
                "Felipe",
                "123.456.789-10",
                "12345",
                "Jojo@gmail.com",
                UserType.COMMON
        );
        sender.deposit(BigDecimal.valueOf(800));

        User recieve = new User(
                "Márcio",
                "Manoel",
                "325.555.987-10",
                "25456",
                "Mama@gmail.com",
                UserType.COMMON
        );
        recieve.deposit(BigDecimal.valueOf(600));

        userRepository.save(sender);
        userRepository.save(recieve);

        String json =String.format("""
                {
                    "value": 200,
                    "senderId": %s,
                    "recieverId": %s
                }
                """, sender.getId(), recieve.getId());

        MvcResult result = mvc.perform(
             post("/transactions")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(json)
        ).andExpect(status().isCreated()).andReturn();

        TransactionGetDTO getDto = mapper.readValue(result.getResponse().getContentAsString(), TransactionGetDTO.class);

        Transaction transaction = transactionRepository.findById(getDto.id()).orElseThrow();
        User senderUser = userRepository.findByDocument("123.456.789-10").orElseThrow();
        User recieverUser = userRepository.findByDocument("325.555.987-10").orElseThrow();

        assertEquals(senderUser.getId(), transaction.getSender().getId());
        assertEquals(recieverUser.getId(), transaction.getReceiver().getId());
        assertEquals(getDto.id(), transaction.getId());
        assertEquals(0, getDto.value().compareTo(transaction.getAmount()));
        assertEquals(getDto.idSender(), transaction.getSender().getId());
        assertEquals(getDto.idReciever(), transaction.getReceiver().getId());
        assertEquals(0, BigDecimal.valueOf(600).compareTo(senderUser.getBalance()));
        assertEquals(0, BigDecimal.valueOf(800).compareTo(recieverUser.getBalance()));
    }
}
