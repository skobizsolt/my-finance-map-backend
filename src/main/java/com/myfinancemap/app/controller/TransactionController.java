package com.myfinancemap.app.controller;

import com.myfinancemap.app.dto.TransactionDto;
import com.myfinancemap.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/{userId}/transactions")
    @Operation(summary = "List all user specific transactions")
    public ResponseEntity<List<TransactionDto>> getAllUserData(@PathVariable final Long userId) {
        return ResponseEntity.ok().body(transactionService.getTransactionListById(userId));
    }
}
