package com.picpaySimplificado.picpaySimplificado.Respository;

import com.picpaySimplificado.picpaySimplificado.Domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
