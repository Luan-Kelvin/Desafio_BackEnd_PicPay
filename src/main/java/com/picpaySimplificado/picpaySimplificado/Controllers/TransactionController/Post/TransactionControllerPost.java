package com.picpaySimplificado.picpaySimplificado.Controllers.TransactionController.Post;

import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionGetDTO;
import com.picpaySimplificado.picpaySimplificado.DTOs.TransactionPostDTO;
import com.picpaySimplificado.picpaySimplificado.Services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionControllerPost {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionGetDTO> createTransaction(@RequestBody TransactionPostDTO dto) throws IOException, InterruptedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(dto));
    }
}
