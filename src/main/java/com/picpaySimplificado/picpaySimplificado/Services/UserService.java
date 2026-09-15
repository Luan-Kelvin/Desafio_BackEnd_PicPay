package com.picpaySimplificado.picpaySimplificado.Services;

import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InsufficientFundsForTransactionException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InvalidUserTypeException;
import com.picpaySimplificado.picpaySimplificado.Exceptions.UserNotFoundException;
import com.picpaySimplificado.picpaySimplificado.Respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void validateTransaction(User user, BigDecimal value) {
        if (user.getUserType() != UserType.COMMON){
            throw new InvalidUserTypeException("Usuário do tipo lojist não esta autorizado a realizar transação");
        }

        if (user.getBalance().compareTo(value) < 0){
            throw new InsufficientFundsForTransactionException("Usuário não tem saldo suficiente para transação.");
        }
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Erro! Usuário não encontrado."));
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }
}
