package com.myfinancemap.app.controller;

import com.myfinancemap.app.dto.CreateTransactionDto;
import com.myfinancemap.app.dto.TransactionDto;
import com.myfinancemap.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<TransactionDto>> getAllTransactionDataByUser(@PathVariable final Long userId) {
        return ResponseEntity.ok().body(transactionService.getTransactionListById(userId));
    }

    @PostMapping(value = "/{userId}/transactions/new")
    @Operation(summary = "Create new transaction")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable final Long userId,
                                                            @Valid @RequestBody
                                                            final CreateTransactionDto createTransactionDto) {
        return ResponseEntity.ok().body(transactionService.createTransaction(userId, createTransactionDto));
    }

    @PutMapping(value = "/transactions/update")
    @Operation(summary = "Update new transaction")
    public ResponseEntity<TransactionDto> updateTransaction(@Valid @RequestBody
                                                            final CreateTransactionDto createTransactionDto) {
        return ResponseEntity.ok().body(transactionService.updateTransaction(createTransactionDto));
    }
}
