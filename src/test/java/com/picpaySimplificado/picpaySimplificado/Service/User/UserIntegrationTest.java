package com.picpaySimplificado.picpaySimplificado.Service.User;

import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Deve criar um usuario e retornar um DTO.")
    void deveCriarUsuarioERetornarDTO() throws Exception {
        String json = """
                {
                    "firsName": "Joaquim",
                    "lastName": "Freitas",
                    "document": "123.456.789-20",
                    "password": "12345",
                    "balance": 500,
                    "email": "Jojo@gmail.com",
                    "type": "COMMON"
                }
                """;

        MvcResult result = mvc.perform(
                post("http://localhost:8080/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated()).andReturn();

        UserGetDTO getDto = mapper.readValue(result.getResponse().getContentAsString(), UserGetDTO.class);

        User userSave = userRepository.findById(getDto.id())
                .orElseThrow();

        assertEquals(getDto.firsName(), userSave.getFirstName());
        assertEquals(getDto.lastName(), userSave.getLastName());
        assertEquals(getDto.email(), userSave.getEmail());
        assertEquals(getDto.type(), userSave.getUserType());
        assertEquals("123.456.789-20", userSave.getDocument());
        assertEquals("12345", userSave.getPassword());
    }

}
