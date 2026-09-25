package com.picpaySimplificado.picpaySimplificado.Services;

import com.picpaySimplificado.picpaySimplificado.Conversor.Conversor;
import com.picpaySimplificado.picpaySimplificado.DTOs.DepositDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserFindByDocumentDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.UserPostDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InsufficientFundsForTransactionException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InvalidUserTypeException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.UserAlreadyExistsException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.UserNotFoundException;
import com.picpaySimplificado.picpaySimplificado.Respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Conversor conversor;

    public UserGetDTO createUser(UserPostDTO dto){
        Optional<User> userRepo = userRepository.findByDocument(dto.document());

        if (userRepo.isPresent()){
            throw new UserAlreadyExistsException(String.format("ERRO! usuário com documento %s já existe.", dto.document()));
        }

        User user = new User(dto.firstName(), dto.lastName(), dto.document(), dto.password(), dto.email(), dto.type());
        userRepository.save(user);

        return conversor.converterUser(user);

    }

    public List<UserGetDTO> listUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map(conversor::converterUser).toList();
    }

    public UserGetDTO deposit(DepositDTO dto){
        User user = userRepository.findByDocument(dto.document())
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com documento: %s não encontrado.", dto.document())));

        user.deposit(dto.value());
        userRepository.save(user);

        return conversor.converterUser(user);
    }

    public void validateTransaction(User user, BigDecimal value) {
        if (user.getUserType() != UserType.COMMON){
            throw new InvalidUserTypeException("Usuário do tipo lojista não esta autorizado a realizar transação");
        }

        if (user.getBalance().compareTo(value) < 0){
            throw new InsufficientFundsForTransactionException("Usuário não tem saldo suficiente para transação.");
        }
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Erro! Usuário não encontrado."));
    }

    public UserFindByDocumentDTO findByDocument(String document){
        User user = userRepository.findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com documento Nº %s não foi encontrado.", document)));

        return conversor.converterUserAndDocument(user);
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }
}
