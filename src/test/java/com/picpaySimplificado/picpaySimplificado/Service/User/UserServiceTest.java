package com.picpaySimplificado.picpaySimplificado.Service.User;


import com.picpaySimplificado.picpaySimplificado.Conversor.Conversor;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserPostDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InsufficientFundsForTransactionException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InvalidUserTypeException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.UserAlreadyExistsException;
import com.picpaySimplificado.picpaySimplificado.Respository.UserRepository;
import com.picpaySimplificado.picpaySimplificado.Services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Conversor conversor;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Deve criar novo usuario e retornar DTO")
    void deveCriarNovoUser() {
        UserPostDTO postDto = new UserPostDTO(
                "Joaquim Freitas",
                "Da Silva",
                "123.456.789-10",
                "12345",
                BigDecimal.valueOf(500),
                "Joaquim@Gmail.com",
                UserType.COMMON
        );

        UserGetDTO dtoGet = new UserGetDTO(1L, "Joaquim Freitas", "Da Silva", "Joaquim@Gmail.com", UserType.COMMON);

        when(userRepository.findByDocument(postDto.document())).thenReturn(Optional.empty());
        when(conversor.converterUser(any(User.class))).thenReturn(dtoGet);

        UserGetDTO getDTO = userService.createUser(postDto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User user = captor.getValue();

        assertEquals(postDto.document(), user.getDocument());
        assertEquals(postDto.firsName(), user.getFirstName());
        assertEquals(postDto.lastName(), user.getLastName());
        assertEquals(postDto.type(), user.getUserType());
        assertEquals(postDto.email(), user.getEmail());
        assertEquals(postDto.password(), user.getPassword());
        assertEquals(postDto.balance(), user.getBalance());
        assertEquals(user.getFirstName(), getDTO.firsName());
        assertEquals(user.getLastName(), getDTO.lastName());
        assertEquals(user.getEmail(), getDTO.email());

        verify(userRepository).findByDocument(postDto.document());

    }

    @Test
    @DisplayName("Deve lançar exceção se ja existir usuario com aquele documento")
    void deveLancarExcecaoSeusuarioExistir() {
        UserPostDTO postDto = new UserPostDTO(
                "Joaquim Freitas",
                "Da Silva",
                "123.456.789-10",
                "12345",
                BigDecimal.valueOf(500),
                "Joaquim@Gmail.com",
                UserType.COMMON
        );

        User user = new User();

        when(userRepository.findByDocument(postDto.document())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(postDto));

        verifyNoInteractions(conversor);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve retornar um lista de usuarios")
    void deveRetornarListaDeUsuario() {
        User user1 = new User("Joaquim", "Pereira", "123.456.789-20", "12345", BigDecimal.valueOf(500), "Jojo@gmail.com", UserType.COMMON);
        User user2 = new User("Francisco", "Leandro", "122.456.789-20", "12345", BigDecimal.valueOf(500), "vano@gmail.com", UserType.COMMON);

        UserGetDTO dtoGet1 = new UserGetDTO(1L, "Joaquim", "Pereira", "Jojo@gmail.com", UserType.COMMON);
        UserGetDTO dtoGet2 = new UserGetDTO(2L, "Francisco", "Leandro", "vano@gmail.com", UserType.COMMON);

        List<User> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);
        when(conversor.converterUser(user1)).thenReturn(dtoGet1);
        when(conversor.converterUser(user2)).thenReturn(dtoGet2);

        List<UserGetDTO> dtos = userService.listUsers();

        assertEquals(dtos.get(0).firsName(), users.get(0).getFirstName());
        assertEquals(dtos.get(0).lastName(), users.get(0).getLastName());
        assertEquals(dtos.get(0).email(), users.get(0).getEmail());
        assertEquals(dtos.get(0).type(), users.get(0).getUserType());
        assertEquals(dtos.get(1).firsName(), users.get(1).getFirstName());
        assertEquals(dtos.get(1).lastName(), users.get(1).getLastName());
        assertEquals(dtos.get(1).email(), users.get(1).getEmail());
        assertEquals(dtos.get(1).type(), users.get(1).getUserType());

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve lançar exceção se usuário nao tiver saldo para transferência")
    void deveLancarExcecaoSeUsuarioEstiverSemSaldoParaTransferencia(){
        User user = new User("Leandro", "martins", "123.456.789-10", "12345", BigDecimal.valueOf(200), "jogger@gmail.com", UserType.COMMON);

        assertThrows(InsufficientFundsForTransactionException.class, () -> userService.validateTransaction(user, BigDecimal.valueOf(400)));
    }

    @Test
    @DisplayName("Deve lançar exceção se tipo de usuario for diferente de COMMON")
    void deveLancarExcecaoSeUsuarioNaoForCommon(){
        User user = new User("Leandro", "martins", "123.456.789-10", "12345", BigDecimal.valueOf(500), "jogger@gmail.com", UserType.SHOPKEEPER);

        assertThrows(InvalidUserTypeException.class, () -> userService.validateTransaction(user, BigDecimal.valueOf(200)));
    }


}