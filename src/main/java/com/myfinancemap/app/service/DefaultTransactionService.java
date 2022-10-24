package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.CreateTransactionDto;
import com.myfinancemap.app.dto.TransactionDto;
import com.myfinancemap.app.mapper.TransactionMapper;
import com.myfinancemap.app.persistence.domain.Transaction;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.TransactionRepository;
import com.myfinancemap.app.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Default implementation of Transaction service.
 */
@Service
public class DefaultTransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final UserRepository userRepository;

    public DefaultTransactionService(TransactionRepository transactionRepository,
                                     TransactionMapper transactionMapper,
                                     UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getTransactionListById(final Long userId) {
        return transactionMapper.toTransactionDtoList(transactionRepository.findByUserUserId(userId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public TransactionDto createTransaction(final Long userId,
                                            final CreateTransactionDto createTransactionDto) {
        final Transaction transaction = transactionMapper.toTransaction(createTransactionDto);
        transaction.setUser(userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {throw new NoSuchElementException();}));
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionDto(transaction);
    }

    @Override
    public TransactionDto updateTransaction(final CreateTransactionDto createTransactionDto) {
        final Transaction transaction = transactionRepository.getTransactionByTransactionId(
                createTransactionDto.getTransactionId())
                .orElseThrow(() -> {throw new NoSuchElementException();});
        final User user = transaction.getUser();
        transactionMapper.modifyTransaction(createTransactionDto, transaction);
        transaction.setUser(user);
        transactionRepository.saveAndFlush(transaction);
        return transactionMapper.toTransactionDto(transaction);
    }
}
