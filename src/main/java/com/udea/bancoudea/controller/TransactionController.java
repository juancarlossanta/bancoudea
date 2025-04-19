package com.udea.bancoudea.controller;

import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionFacade;

    public TransactionController(TransactionService transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    // Realizar la transacción de movimiento de dinero entre cuentas
    @PostMapping
    public ResponseEntity<TransactionDTO> transferMoney(@RequestBody TransferRequestDTO transferRequestDTO) {
        if(transferRequestDTO.getAmount() == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if(transferRequestDTO.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount cannot be 0 or negative");
        }
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setSenderAccountNumber(transferRequestDTO.getSenderAccountNumber());
        transactionDTO.setReceiverAccountNumber(transferRequestDTO.getReceiverAccountNumber());
        transactionDTO.setAmount(transferRequestDTO.getAmount());
        transactionDTO.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(transactionFacade.transferMoney(transactionDTO));
    }

    // Consultar historial de transacciones por número de cuenta
    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsForAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionFacade.getTransactionsForAccount(accountNumber));
    }
}
