package com.picpaySimplificado.picpaySimplificado.Controllers.TransactionController.Get;

import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.Services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionControllerGet {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionGetDTO>> listTransactions(){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.listTransactions());
    }
}
