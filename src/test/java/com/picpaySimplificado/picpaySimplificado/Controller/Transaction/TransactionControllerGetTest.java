package com.picpaySimplificado.picpaySimplificado.Controller.Transaction;

import com.picpaySimplificado.picpaySimplificado.Controllers.TransactionController.Get.TransactionControllerGet;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.Services.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionControllerGet.class)
public class TransactionControllerGetTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    @DisplayName("Deve retornar Status 200 - OK quando solicitar lista de transactions")
    void deveRetornarStatus200QuandoRetornarLista() throws Exception {
        List<TransactionGetDTO> list = new ArrayList<>();

        when(transactionService.listTransactions()).thenReturn(list);

        mvc.perform(get("http://localhost:8080/transactions")).andExpect(status().isOk());

        verify(transactionService).listTransactions();
    }
}
