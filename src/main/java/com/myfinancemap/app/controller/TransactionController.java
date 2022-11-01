package com.myfinancemap.app.controller;

import com.myfinancemap.app.dto.TotalCostResponse;
import com.myfinancemap.app.dto.transaction.TransactionDto;
import com.myfinancemap.app.service.interfaces.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Controller for listing all user specific transactions.
     *
     * @param userId id of the user
     * @return a List of Transactions.
     */
    @GetMapping(value = "/{userId}")
    @Operation(summary = "List all user specific transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactionDataByUser(@PathVariable final Long userId) {
        log.info("Endpoint invoked. userId = {}", userId);
        return ResponseEntity.ok().body(transactionService.getTransactionListById(userId));
    }

    /**
     * Controller for creating a transaction.
     *
     * @param userId         id of the existing user
     * @param transactionDto given data for creating the transaction
     * @return the created Transaction as a dto.
     */
    @PostMapping(value = "/{userId}/new")
    @Operation(summary = "Create new transaction")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable final Long userId,
                                                            @Valid @RequestBody final TransactionDto transactionDto) {
        log.info("Endpoint invoked. userId = {}, transactionDto = {}", userId, transactionDto);
        return ResponseEntity.ok().body(transactionService.createTransaction(userId, transactionDto));
    }

    /**
     * Controller for updating an existing Transaction.
     *
     * @param transactionDto given data for creating the transaction.
     * @return the updated Transaction as a dto.
     */
    @PutMapping(value = "/update")
    @Operation(summary = "Update existing transaction")
    public ResponseEntity<TransactionDto> updateTransaction(@Valid @RequestBody final TransactionDto transactionDto) {
        log.info("Endpoint invoked. transactionDto = {}", transactionDto);
        return ResponseEntity.ok().body(transactionService.updateTransaction(transactionDto));
    }

    /**
     * Controller to delete existing transaction
     *
     * @param transactionId id of the transaction
     * @return 200 OK if the transaction is found.
     */
    @DeleteMapping
    @Operation(summary = "Delete existing transaction")
    public ResponseEntity<Void> deleteTransaction(@RequestParam final Long transactionId) {
        log.info("Endpoint invoked. transactionId = {}", transactionId);
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().build();
    }

    /**
     * Controller for listing all user specific transactions by currency.
     *
     * @param userId id of the user
     * @return a List of Transactions.
     */
    @GetMapping(value = "/{userId}", params = "currency")
    @Operation(summary = "List all user specific transactions by currency")
    public ResponseEntity<List<TransactionDto>> getAllTransactionDataByUserAndCurrency(@PathVariable final Long userId,
                                                                                       @RequestParam final String currency) {
        log.info("Endpoint invoked. userId = {}, currency = {}", userId, currency);
        return ResponseEntity.ok().body(transactionService.getTransactionListByIdAndCurrency(userId, currency));
    }

    /**
     * Controller for listing all user specific transactions by type.
     *
     * @param userId   id of the user
     * @param isIncome income if true, expense if false
     * @return a List of Transactions.
     */
    @GetMapping(value = "/{userId}", params = "isIncome")
    @Operation(summary = "List all user specific transactions by type")
    public ResponseEntity<List<TransactionDto>> getTransactionsByIncomeOrOutcome(@PathVariable final Long userId,
                                                                                 @RequestParam final Boolean isIncome) {
        log.info("Endpoint invoked. userId = {}, isIncome = {}", userId, isIncome);
        return ResponseEntity.ok().body(transactionService.getIncomeOrOutcome(userId, isIncome));
    }

    @GetMapping(value = "/{userId}", params = {"fromDate", "toDate"})
    @Operation(summary = "List all user specific transactions by specific interval")
    public ResponseEntity<List<TransactionDto>> getTransactionsByInterval(@PathVariable final Long userId,
                                                                          @RequestParam("fromDate")
                                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                          @PastOrPresent final LocalDate fromDate,
                                                                          @RequestParam("toDate")
                                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                          @PastOrPresent final LocalDate toDate) {
        log.info("Endpoint invoked. userId = {}, fromDate = {}, toDate = {}", userId, fromDate, toDate);
        return ResponseEntity.ok().body(transactionService.getTransactionsByInterval(userId, fromDate, toDate));
    }

    /**
     * Controller for total income/outcome by type.
     *
     * @param userId   id of the user
     * @param isIncome income if true, expense if false
     * @return a List of Transactions.
     */
    @GetMapping(value = "/{userId}/total", params = "isIncome")
    @Operation(summary = "Get total income or outcome")
    public ResponseEntity<TotalCostResponse> getTotalIncomeOrOutcome(@PathVariable final Long userId,
                                                                     @RequestParam final Boolean isIncome) {
        log.info("Endpoint invoked. userId = {}, isIncome = {}", userId, isIncome);
        return ResponseEntity.ok().body(transactionService.getTotal(userId, isIncome));
    }
}
