package com.picpaySimplificado.picpaySimplificado.Controller.User;

import com.picpaySimplificado.picpaySimplificado.Controllers.UserController.Patch.UserControllerPatch;
import com.picpaySimplificado.picpaySimplificado.DTOs.DepositDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserControllerPatch.class)
public class UserControllerPatchTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Deve retornar Status 200 - OK sedepósito for feito com sucesso!")
    void deveRetornar200AposDeposito() throws Exception {
        String json = """
                {
                    "document": "123.456.789-10",
                    "value": 200
                }
                """;

        UserGetDTO dtoGet = new UserGetDTO(1L, "Joaquim", "Pereira", BigDecimal.ZERO, "Jojo@gmail.com", UserType.COMMON);

        when(userService.deposit(any(DepositDTO.class))).thenReturn(dtoGet);

        mvc.perform(
                patch("http://localhost:8080/user/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(userService).deposit(any(DepositDTO.class));
    }
}
