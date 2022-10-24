package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.CreateTransactionDto;
import com.myfinancemap.app.dto.TransactionDto;

import java.util.List;

/**
 * Interface for Transaction service.
 */
public interface TransactionService {
    /**
     * Method for listing all user relevant transactions.
     *
     * @param userId id of the user we want all the transactions from
     * @return List, containing TransactionDtos.
     */
    List<TransactionDto> getTransactionListById(final Long userId);

    /**
     * Method for creating new transactions.
     *
     * @param userId id of the user
     * @param createTransactionDto data from json
     * @return TransactionDto, containing created Transaction data.
     */
    TransactionDto createTransaction(final Long userId,
                                     final CreateTransactionDto createTransactionDto);

    /**
     * Method for updating an existing transaction
     *
     * @param createTransactionDto data from json
     * @return TransactionDto, containing updated Transaction data.
     */
    TransactionDto updateTransaction(final CreateTransactionDto createTransactionDto);
}
