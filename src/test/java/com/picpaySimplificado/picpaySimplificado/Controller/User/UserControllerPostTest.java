package com.picpaySimplificado.picpaySimplificado.Controller.User;

import com.picpaySimplificado.picpaySimplificado.Controllers.UserController.Post.UserControllerPost;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserPostDTO;
import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Exceptions.UserAlreadyExistsException;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerPost.class)
public class UserControllerPostTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Deve retornar Status 201 - CREATED, Se usuario for criado com sucesso! ")
    void devRetornar201SeUsuarioForCriado() throws Exception {
        String json = """
                {
                    "firstName": "Joaquim",
                    "lastName": "Golveia",
                    "document": "123.456.789-10",
                    "password": "12345",
                    "balance": 500,
                    "email": "joaquim@gmail.com",
                    "type": "COMMON"
                }
                """;

        UserGetDTO getDTO = new UserGetDTO(
                1L,
                "Joaquim",
                "Golveia",
                "joaquim@gmail.com",
                UserType.COMMON
        );

        when(userService.createUser(any(UserPostDTO.class))).thenReturn(getDTO);

        mvc.perform(
                post("http://localhost:8080/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());

        verify(userService).createUser(any(UserPostDTO.class));
    }

    @Test
    @DisplayName("Deve lançar Status 409 - CONFLICT se usuário ja existir.")
    void deveLancar409SeUsuarioJaExistir() throws Exception {
        String json = """
                {
                    "firstName": "Joaquim",
                    "lastName": "Golveia",
                    "document": "123.456.789-10",
                    "password": "12345",
                    "balance": 500,
                    "email": "joaquim@gmail.com",
                    "type": "COMMON"
                }
                """;

        doThrow(new UserAlreadyExistsException("ERRO! usuário já existe."))
                .when(userService).createUser(any(UserPostDTO.class));

        mvc.perform(
                post("http://localhost:8080/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isConflict());

        verify(userService).createUser(any(UserPostDTO.class));
    }
}
