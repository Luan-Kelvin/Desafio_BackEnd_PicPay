package com.picpaySimplificado.picpaySimplificado.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @ManyToOne()
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "reciever_id")
    private User reciever;

    private LocalDateTime timesTamp;

    public Transaction(BigDecimal amount, User sender, User reciever) {
        this.amount = amount;
        this.sender = sender;
        this.reciever = reciever;
        this.timesTamp = LocalDateTime.now();
    }
}
