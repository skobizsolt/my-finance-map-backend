package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.TransactionDto;

import java.util.List;

/**
 * Interface for Transaction service.
 */
public interface TransactionService {
    /**
     * @param userId id of the user we want all the transactions from
     * @return List, containing TransactionDtos.
     */
    List<TransactionDto> getTransactionListById(final Long userId);
}
