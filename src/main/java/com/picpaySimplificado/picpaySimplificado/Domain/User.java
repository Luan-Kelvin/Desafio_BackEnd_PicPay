package com.picpaySimplificado.picpaySimplificado.Domain;

import com.picpaySimplificado.picpaySimplificado.Enum.UserType;
import com.picpaySimplificado.picpaySimplificado.Exceptions.InvalidTransferAmountException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    private String password;

    private BigDecimal balance;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(String firstName, String lastName, String document, String password, BigDecimal balance, String email, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.document = document;
        this.password = password;
        this.balance = balance;
        validEmail(email);
        this.userType = userType;
    }

    public void recieverTransfer(BigDecimal value){
        if (value.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransferAmountException("ERRO! valor de transferência inválido.");
        }

        this.balance = balance.add(value);
    }

    public void senderTransfer(BigDecimal value){
        if (value.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransferAmountException("ERRO! valor de transferência inválido.");
        }

        this.balance = balance.subtract(value);
    }

    private void validEmail(String email){
        if (email.matches("^[A-Za-z0-9 .*&#!_+%-]+@[A-Za-z]+\\.[a-z]{2,}+$")){
            this.email = email;
        }else {
            throw new IllegalArgumentException("ERRO! Formato de email inválido.");
        }
    }

}
