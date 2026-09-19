package com.picpaySimplificado.picpaySimplificado.Controller.User;


import com.picpaySimplificado.picpaySimplificado.Controllers.UserController.Get.UserControllerGet;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
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

@WebMvcTest(UserControllerGet.class)
public class UserControllerGetTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Deve retornar Status 200 - OK quando for listado todos os usuarios.")
    void deveRetornar200QuandoRetornarListaDeUsuarios() throws Exception {
        List<UserGetDTO> list = new ArrayList<>();

        when(userService.listUsers()).thenReturn(list);

        mvc.perform(get("http://localhost:8080/user")).andExpect(status().isOk());

        verify(userService).listUsers();
    }
}
