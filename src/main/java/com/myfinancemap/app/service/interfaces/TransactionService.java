package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.TotalCostResponse;
import com.myfinancemap.app.dto.transaction.TransactionDto;

import java.time.LocalDate;
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
     * @param userId         id of the user
     * @param transactionDto data from json
     * @return TransactionDto, containing created Transaction data.
     */
    TransactionDto createTransaction(final Long userId,
                                     final TransactionDto transactionDto);

    /**
     * Method for updating an existing transaction
     *
     * @param transactionDto data from json
     * @return TransactionDto, containing updated Transaction data.
     */
    TransactionDto updateTransaction(final TransactionDto transactionDto);

    /**
     * Method for deleting an existing transaction.
     *
     * @param transactionId id of the transaction.
     */
    void deleteTransaction(final Long transactionId);

    /**
     * Method for listing all user relevant transactions by currency.
     *
     * @param currency the value we want the transactions from
     * @param userId   id of the user we want all the transactions from
     * @return List, containing TransactionDtos.
     */
    List<TransactionDto> getTransactionListByIdAndCurrency(final Long userId, final String currency);

    /**
     * Method for listing all user specific transactions by type.
     *
     * @param userId   id of the user
     * @param isIncome income if true, expense if false
     * @return a List of Transactions.
     */
    List<TransactionDto> getIncomeOrOutcome(final Long userId, final Boolean isIncome);

    /**
     * Method for listing transactions by interval
     *
     * @param userId id of the user
     * @param fromDate start date we want entries from
     * @param toDate end date we want entries from
     * @return a List of Transactions.
     */
    List<TransactionDto> getTransactionsByInterval(final Long userId,
                                                   final LocalDate fromDate,
                                                   final LocalDate toDate);
    /**
     * Method for display total income/outcome by type.
     *
     * @param userId   id of the user
     * @param isIncome income if true, expense if false
     * @return total income/outcome.
     */
    TotalCostResponse getTotal(final Long userId, final Boolean isIncome);

}
