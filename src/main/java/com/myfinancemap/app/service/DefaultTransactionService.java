package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.TransactionDto;
import com.myfinancemap.app.mapper.TransactionMapper;
import com.myfinancemap.app.persistence.repository.TransactionRepository;

import java.util.List;

/**
 * Default implementation of Transaction service.
 */
public class DefaultTransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public DefaultTransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getTransactionListById(final Long userId) {
        return transactionMapper.transactionsToTransactionDtoList(transactionRepository.getTransactionByUserId(userId));
    }
}
